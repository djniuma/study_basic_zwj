package com.basic.service;

import com.basic.common.HttpResult;
import com.basic.param.PublishDynamicParam;

public interface DynamicManage {

    public HttpResult dealDynamicPublishRequest(PublishDynamicParam param);

    public HttpResult dealDynamicExtendContent(String extendContent);

    public Integer getType();
}
