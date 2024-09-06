package com.basic.result;

import com.basic.vo.DynamicVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicPageResult {

    private Integer total;

    private List<DynamicVO> data;

    private Map<Integer, String> dynamicStatusEnum = new HashMap<>();

    private Map<Integer, String> dynamicTypeEnum = new HashMap<>();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DynamicVO> getData() {
        return data;
    }

    public void setData(List<DynamicVO> data) {
        this.data = data;
    }

    public Map<Integer, String> getDynamicStatusEnum() {
        return dynamicStatusEnum;
    }

    public void setDynamicStatusEnum(Map<Integer, String> dynamicStatusEnum) {
        this.dynamicStatusEnum = dynamicStatusEnum;
    }

    public Map<Integer, String> getDynamicTypeEnum() {
        return dynamicTypeEnum;
    }

    public void setDynamicTypeEnum(Map<Integer, String> dynamicTypeEnum) {
        this.dynamicTypeEnum = dynamicTypeEnum;
    }
}
