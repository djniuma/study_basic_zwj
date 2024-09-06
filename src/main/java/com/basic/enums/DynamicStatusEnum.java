package com.basic.enums;

import java.util.HashMap;
import java.util.Map;

public enum DynamicStatusEnum {

    NORMAL(1, "正常", "NORMAL"),

    DEL(2, "删除", "DEL"),

    HIDE(3, "隐藏", "HIDE"),

    ;

    private Integer status;

    private String desc;

    private String name;

    private DynamicStatusEnum(Integer status, String desc, String name) {
        this.status = status;
        this.desc = desc;
        this.name = name;
    }

    private static Map<Integer, String> dynamicStatusMap = new HashMap<>();

    static {
        for(DynamicStatusEnum temp : DynamicStatusEnum.values()) {
            dynamicStatusMap.put(temp.getStatus(), temp.getDesc());
        }
    }

    public static Map<Integer, String> getDynamicStatusMap() {
        return dynamicStatusMap;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }
}
