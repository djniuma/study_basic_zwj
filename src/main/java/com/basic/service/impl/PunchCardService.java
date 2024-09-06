package com.basic.service.impl;

import com.basic.common.HttpResult;
import com.basic.contant.DynamicContant;
import com.basic.dto.DynamicDTO;
import com.basic.dto.PunchCardDTO;
import com.basic.enums.DynamicStatusEnum;
import com.basic.enums.DynamicTypeEnum;
import com.basic.exception.DynamicException;
import com.basic.local.HostHolder;
import com.basic.mapper.DynamicMapper;
import com.basic.param.PublishDynamicParam;
import com.basic.param.PunchCardParam;
import com.basic.service.DynamicManage;
import com.basic.service.DynamicService;
import com.basic.util.CommonUtil;
import com.basic.util.JsonUtils;

import com.basic.vo.PunchCardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class PunchCardService implements DynamicManage {

    final static Logger logger = LoggerFactory.getLogger(PunchCardService.class);

    /**
     * 获取当前线程用户的用户id
     */
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    public HttpResult dealDynamicPublishRequest(PublishDynamicParam param) {
        String userId = hostHolder.getUser().getId();

        logger.info("开始打卡发布, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
        PunchCardParam punchCardParam = param.getPunchCardParam();
        if(ObjectUtils.isEmpty(punchCardParam)) {
            logger.info("开始打卡发布, punchCardParam请求体不能为空, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.PUNCHCARDPARAMA_IS_NOT_NULL);
        }
        Date punchCardDate = punchCardParam.getPunchCardDate();
        if(ObjectUtils.isEmpty(punchCardDate)) {
            logger.info("开始打卡发布, 打卡时间不能为空, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.PUNCHCARDDATE_IS_NOT_NULL);
        }
        if(!CommonUtil.getNowDayFormat(new Date()).equals(CommonUtil.getNowDayFormat(punchCardDate))) {
            logger.info("开始打卡发布, 打卡时间错误, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.PUNCHCARDDATE_IS_ERROR);
        }
        String punchCardContent = punchCardParam.getPunchCardContent();
        if(ObjectUtils.isEmpty(punchCardContent)) {
            logger.info("开始打卡发布, 打卡内容不能为空, 请求参数: {}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(DynamicException.PUNCHCARDCONTENT_IS_NOT_NULL);
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


        PunchCardDTO punchCardDTO = new PunchCardDTO();
        punchCardDTO.setId(CommonUtil.createUUID());
        punchCardDTO.setPunchCardDate(punchCardDate);
        punchCardDTO.setPunchCardContent(punchCardContent);
        punchCardDTO.setDynamicId(dynamicDTO.getId());

        dynamicDTO.setExtendContent(JsonUtils.objectToJson(punchCardDTO));

        dynamicMapper.insertDynamic(dynamicDTO);
        return HttpResult.ok();
    }

    @Override
    public HttpResult dealDynamicExtendContent(String extendContent) {
        return new HttpResult<>(JsonUtils.jsonToPojo(extendContent, PunchCardVO.class));
    }

    @Override
    public Integer getType() {
        return DynamicTypeEnum.PUNCH_CARD.getType();
    }
}
