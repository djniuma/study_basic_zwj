package com.basic.service.impl;

import com.basic.common.HttpResult;
import com.basic.contant.DynamicContant;
import com.basic.dto.DynamicDTO;
import com.basic.dto.VoteDTO;
import com.basic.dto.VoteOptionDTO;
import com.basic.enums.DynamicStatusEnum;
import com.basic.enums.DynamicTypeEnum;
import com.basic.exception.DynamicException;
import com.basic.local.HostHolder;
import com.basic.mapper.DynamicMapper;
import com.basic.param.PublishDynamicParam;
import com.basic.param.VoteOptionParam;
import com.basic.param.VoteParam;
import com.basic.service.DynamicManage;
import com.basic.util.CommonUtil;
import com.basic.util.JsonUtils;

import com.basic.vo.VoteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VoteService implements DynamicManage {

    final static Logger logger = LoggerFactory.getLogger(VoteService.class);

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    public HttpResult dealDynamicPublishRequest(PublishDynamicParam param) {
        String userId = hostHolder.getUser().getId();
        logger.info("开始发布投票动态, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
        VoteParam voteParam = param.getVoteParam();
        if(ObjectUtils.isEmpty(voteParam)) {
            logger.info("开始发布投票动态, voteParam请求体不能为空, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.VOTEPARAM_IS_NOT_NULL);
        }
        String voteTitle = voteParam.getVoteTitle();
        if(ObjectUtils.isEmpty(voteTitle)) {
            logger.info("开始发布投票动态, 投票标题不能为空, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.VOTETITLE_IS_NOT_NULL);
        }
        List<VoteOptionParam> optionList = voteParam.getOptionList();
        if(ObjectUtils.isEmpty(optionList)) {
            logger.info("开始发布投票动态, 投票选项不能为空, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.VOTE_OPTION_IS_NOT_NULL);
        }
        if(optionList.size() < DynamicContant.DEFAULT_VOTE_OPTION_SIZE_MIN_VALUE) {
            logger.info("开始发布投票动态, 投票选项不能小于2, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.VOTE_OPTION_IS_NOT_LESS_TWO);
        }
        for (VoteOptionParam item : optionList) {
            if(StringUtils.isEmpty(item.getContent())) {
                logger.info("开始发布投票动态, 投票选项内容不能为空, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(DynamicException.VOTE_OPTION_CONTENT_IS_NOT_NULL);
            }
        }


        DynamicDTO dynamicDTO = new DynamicDTO();
        dynamicDTO.setId(CommonUtil.createUUID());
        dynamicDTO.setUserId(userId);
        dynamicDTO.setTitle(param.getTitle());
        dynamicDTO.setContent(param.getContent());
        dynamicDTO.setImgArray(JsonUtils.objectToJson(param.getImgs()));
        dynamicDTO.setType(param.getType());
        dynamicDTO.setStatus(DynamicStatusEnum.NORMAL.getStatus());
        dynamicDTO.setCommentCount(DynamicContant.DEFAULT_DYNAMIC_COMMENT_COUNT);
        dynamicDTO.setCreatedDate(new Date());
        dynamicDTO.setUpdateDate(new Date());

        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setId(CommonUtil.createUUID());
        voteDTO.setDynamicId(dynamicDTO.getId());
        voteDTO.setVoteTitle(voteTitle);
        voteDTO.setVoteTakeCount(DynamicContant.DEFAULT_TAKE_VOTE_COUNT);
        List<VoteOptionDTO> voteOptionDTOS = new ArrayList<>();

        for(int i = 0; i < optionList.size(); i++) {
            VoteOptionParam item = optionList.get(i);
            VoteOptionDTO voteOptionDTO = new VoteOptionDTO();
            voteOptionDTO.setId(CommonUtil.createUUID());
            voteOptionDTO.setVoteId(voteDTO.getId());
            voteOptionDTO.setContent(item.getContent());
            voteOptionDTO.setVoteCount(DynamicContant.DEFAULT_TAKE_VOTE_COUNT);
            voteOptionDTO.setOptionOrder(i);
            voteOptionDTOS.add(voteOptionDTO);
        }
        voteDTO.setOptionList(voteOptionDTOS);

        dynamicDTO.setExtendContent(JsonUtils.objectToJson(voteDTO));

        dynamicMapper.insertDynamic(dynamicDTO);

        return HttpResult.ok();
    }

    @Override
    public HttpResult dealDynamicExtendContent(String extendContent) {
        return new HttpResult<>(JsonUtils.jsonToPojo(extendContent, VoteVO.class));
    }

    @Override
    public Integer getType() {
        return DynamicTypeEnum.VOTE.getType();
    }
}
