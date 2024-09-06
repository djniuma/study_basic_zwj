package com.basic.enums;

import java.util.HashMap;
import java.util.Map;

public enum DynamicTypeEnum {

    BASIC(1, "动态", "BASIC"),

    PUNCH_CARD(2, "打卡", "PUNCH_CARD"),

    VOTE(3, "投票", "VOTE")

    ;

    private Integer type;

    private String desc;

    private String name;

    private DynamicTypeEnum(Integer type, String desc, String name) {
        this.type = type;
        this.desc = desc;
        this.name = name;
    }

    private static Map<Integer, DynamicTypeEnum> dynamicTypeEnumMap = new HashMap<>();

    private static Map<Integer, String> dynamicTypeMap = new HashMap<>();

    static {
        for(DynamicTypeEnum temp : DynamicTypeEnum.values()) {
            dynamicTypeEnumMap.put(temp.getType(), temp);
            dynamicTypeMap.put(temp.getType(), temp.getDesc());
        }
    }

    public static Map<Integer, String> getDynamicTypeMap() {
        return dynamicTypeMap;
    }

    public static DynamicTypeEnum getDynamicTypeEnumMap(Integer type) {
        return dynamicTypeEnumMap.getOrDefault(type, null);
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }
}
