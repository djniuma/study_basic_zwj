package com.basic.service.impl;

import com.basic.common.HttpResult;
import com.basic.contant.DynamicContant;
import com.basic.dto.DynamicDTO;
import com.basic.enums.DynamicStatusEnum;
import com.basic.enums.DynamicTypeEnum;
import com.basic.local.HostHolder;
import com.basic.mapper.DynamicMapper;
import com.basic.param.PublishDynamicParam;
import com.basic.service.DynamicManage;
import com.basic.service.DynamicService;
import com.basic.util.CommonUtil;
import com.basic.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BasicDynamicService implements DynamicManage {

    final static Logger logger = LoggerFactory.getLogger(BasicDynamicService.class);

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    public HttpResult dealDynamicPublishRequest(PublishDynamicParam param) {

        String userId = hostHolder.getUser().getId();

        logger.info("开始基础图文动态, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));

        try {
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
            dynamicMapper.insertDynamic(dynamicDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            logger.error("开始基础图文动态异常, userId: {}, 请求参数: {}", userId, JsonUtils.objectToJson(param));
            e.printStackTrace();
            return HttpResult.fail();
        }
    }

    @Override
    public HttpResult dealDynamicExtendContent(String extendContent) {
        return null;
    }

    @Override
    public Integer getType() {
        return DynamicTypeEnum.BASIC.getType();
    }
}
