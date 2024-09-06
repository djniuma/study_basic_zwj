package com.basic.service;

import com.basic.common.HttpResult;
import com.basic.contant.DynamicContant;

import com.basic.dto.CommentDTO;
import com.basic.dto.DynamicDTO;
import com.basic.dto.DynamicPageDTO;
import com.basic.dto.VoteDTO;
import com.basic.enums.DynamicStatusEnum;
import com.basic.enums.DynamicTypeEnum;
import com.basic.enums.EntityTypeEnum;
import com.basic.exception.DynamicException;
import com.basic.local.HostHolder;
import com.basic.mapper.DynamicMapper;
import com.basic.message.MessageDTO;
import com.basic.message.MessageProducer;
import com.basic.message.MessageTypeEnum;
import com.basic.param.CommentParam;
import com.basic.param.PublishDynamicParam;
import com.basic.param.QueryDynamicPageParam;
import com.basic.param.TakeVoteParam;
import com.basic.result.DynamicPageResult;
import com.basic.util.CommonUtil;
import com.basic.util.JsonUtils;

import com.basic.util.QiniuPictureServiceUtils;
import com.basic.vo.DynamicVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DynamicService {

    final static Logger logger = LoggerFactory.getLogger(DynamicService.class);

    @Autowired
    private List<DynamicManage> dynamicManageList;

    @Autowired
    private DynamicMapper dynamicMapper;

    @Autowired
    private QiniuPictureServiceUtils qiniuPictureServiceUtils;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageProducer messageProducer;


    public HttpResult publishDynamic(PublishDynamicParam param) {
        logger.info("发布动态,请求参数:{}", JsonUtils.objectToJson(param));

        String title = param.getTitle();
        if(StringUtils.isEmpty(title)) {
            logger.info("发布动态, 标题不能为空, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_TITLE_IS_NOT_NULL);
        }

        String content = param.getContent();
        if(StringUtils.isEmpty(title)) {
            logger.info("发布动态, 内容不能为空, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_CONTENT_IS_NOT_NULL);
        }

        Integer type = param.getType();

        if(DynamicTypeEnum.getDynamicTypeEnumMap(type) == null) {
            logger.info("发布动态, 动态类型不存在, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_TYPE_IS_NOT_EXIST);
        }

        dynamicManageList.stream()
                .filter(item -> type.equals(item.getType()))
                .findFirst()
                .get()
                .dealDynamicPublishRequest(param);

        return HttpResult.ok();
    }

    public HttpResult queryDynamicPage(QueryDynamicPageParam param) {
        logger.info("分页查询动态, 请求参数: {}", JsonUtils.objectToJson(param));
        Integer nowPage = param.getNowPage();
        if(ObjectUtils.isEmpty(nowPage)) {
            logger.info("分页查询动态, 当前页为空, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_NOW_PAGE_IS_NOT_NULL);
        }
        if(nowPage < DynamicContant.DEFAULT_NOW_PAGE_MIN_VALUE) {
            logger.info("分页查询动态, 当前页<1, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_NOW_PAGE_IS_NOT_LESS_ONE);
        }

        Integer pageSize = param.getPageSize();
        if(ObjectUtils.isEmpty(pageSize)) {
            logger.info("分页查询动态, 当前页的大小为空, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_PAGE_SIZE_IS_NOT_NULL);
        }
        if(pageSize < DynamicContant.DEFAULT_PAGE_SIZE_MIN_VALUE) {
            logger.info("分页查询动态, 当前页的大小<1, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_PAGE_SIZE_IS_NOT_LESS_ONE);
        }
        Integer count = dynamicMapper.queryDynamicCount();
        if(ObjectUtils.isEmpty(count) || count < DynamicContant.DEFAULT_DYNAMIC_COUNT_MIN_VALUE) {
            logger.info("分页查询动态, 当前表按照条件查询, 数据量为空, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_COUNT_IS_NULL);
        }
        int total = count / pageSize + 1;
        if(nowPage > total) {
            logger.info("分页查询动态, 当前页没有数据, 数据量为空, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_NOW_PAGE_DATE_IS_NULL);
        }
        int start = (nowPage - 1) * pageSize;

        List<DynamicPageDTO> list = dynamicMapper.queryDynamicPage(start, pageSize);

        List<DynamicVO> dynamicVOS = new ArrayList<>();
        list.forEach(item -> {
            DynamicVO dynamicVO = new DynamicVO();
            dynamicVO.setImgs(new ArrayList<>());
            BeanUtils.copyProperties(item, dynamicVO);
            String imgs = item.getImgs();
            List<String> imgList = JsonUtils.jsonToList(imgs, String.class);
            for(int i =0; i < imgList.size(); i++) {
                String fileName = imgList.get(i);
                String url = qiniuPictureServiceUtils.queryImage(fileName);
                dynamicVO.getImgs().add(url);
            }

            if(!DynamicTypeEnum.BASIC.getType().equals(item.getType())) {
                HttpResult extend = dynamicManageList.stream()
                        .filter(temp -> item.getType().equals(temp.getType()))
                        .findFirst()
                        .get()
                        .dealDynamicExtendContent(item.getExtendContent());
                dynamicVO.setExtendContent(extend.getData());
            }

            dynamicVOS.add(dynamicVO);
        });

        DynamicPageResult dynamicPageResult = new DynamicPageResult();
        dynamicPageResult.setData(dynamicVOS);
        dynamicPageResult.setTotal(count);
        dynamicPageResult.setDynamicStatusEnum(DynamicStatusEnum.getDynamicStatusMap());
        dynamicPageResult.setDynamicTypeEnum(DynamicTypeEnum.getDynamicTypeMap());

        return new HttpResult(dynamicPageResult);
    }

    public HttpResult comment(CommentParam param) {
        String userID = hostHolder.getUser().getId();
        logger.info("开始评论, 请求参数:{}", JsonUtils.objectToJson(param));
        Integer entityType = param.getEntity_type();
        if(ObjectUtils.isEmpty(entityType)) {
            logger.info("开始评论, 评论实体类型为空, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        if(EntityTypeEnum.getEntityTypeEnumMap(entityType) == null) {
            logger.info("开始评论, 评论实体类型不正确, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        String entityId = param.getEntity_id();
        if(StringUtils.isEmpty(entityId)) {
            return HttpResult.fail();
        }

        /**
         * TODO 验证entityID所属的实体是否存在
         */

        String content = param.getContent();
        if(StringUtils.isEmpty(content)) {
            return HttpResult.fail();
        }

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(CommonUtil.createUUID());
        commentDTO.setEntity_type(entityType);
        commentDTO.setEntity_id(entityId);
        commentDTO.setContent(content);
        commentDTO.setCreateDate(new Date());
        commentDTO.setUserId(userID);
        /**
         * TODO 写一下comment相关的数据库表和mapper接口及实现的xml文件
         *
         */

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(CommonUtil.createUUID());
        messageDTO.setUserId(userID);
        messageDTO.setType(MessageTypeEnum.COMMENT_MESSAGE.getType());
        messageDTO.setCreateDate(new Date());
        messageDTO.setMessage(JsonUtils.objectToJson(commentDTO));
        logger.info("评论成功，产生异步消息需要处理，消息:{}", JsonUtils.objectToJson(messageDTO));
        messageProducer.produceMessage(JsonUtils.objectToJson(messageDTO));

        return HttpResult.ok();
    }

    public HttpResult vote(TakeVoteParam param) {
        String userID = hostHolder.getUser().getId();
        logger.info("开始投票, userID, 请求参数:{}", userID, JsonUtils.objectToJson(param));
        String dynamicId = param.getDynamicId();
        if (StringUtils.isEmpty(dynamicId)) {
            logger.info("开始投票, 动态Id为空, 请求参数:{}", userID, JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        String voteOptionID = param.getVoteOptionId();
        if (StringUtils.isEmpty(voteOptionID)) {
            logger.info("开始投票, 投票选项Id为空, 请求参数:{}", userID, JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        DynamicDTO dynamicDTO = dynamicMapper.queryDynamicById(dynamicId);
        if (ObjectUtils.isEmpty(dynamicDTO)) {
            logger.info("开始投票, 投票动态查询失败, 请求参数:{}", userID, JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        Integer type = dynamicDTO.getType();
        if (!DynamicTypeEnum.VOTE.getType().equals(type)) {
            logger.info("开始投票,动态类型不是投票类型, 请求参数:{}", userID, JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        String exContent = dynamicDTO.getExtendContent();
        if (StringUtils.isEmpty(exContent)) {
            logger.info("开始投票, 投票选项为空, 请求参数:{}", userID, JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        VoteDTO voteDTO = null;
        try {
            voteDTO = JsonUtils.jsonToPojo(exContent, VoteDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ObjectUtils.isEmpty(voteDTO)) {
            logger.info("开始投票, 投票DTO转化失败, 请求参数:{}", userID, JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
        voteDTO.getOptionList().forEach(item -> {
            if (item.getId().equals(voteOptionID)) {
                item.setVoteCount(item.getVoteCount() + 1);
            }
        });
        voteDTO.setVoteTakeCount(voteDTO.getVoteTakeCount() + 1);
        /**
         * TODO 将信息更新到记录中
         * TODO 作业: 1.完成投票的更新sql; 2.完成消息发送, 被投票的人接受到有人参与投票的消息
         */

        return HttpResult.ok();
    }
}
