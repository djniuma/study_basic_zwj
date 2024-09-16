package com.basic.enums;

import java.util.HashMap;
import java.util.Map;

public enum EntityTypeEnum {

    DYNAMIC(1, "动态", "DYNAMIC"),

    COMMENT(2, "评论", "COMMENT"),

    VOTE(3, "投票", "VOTE")
    ;

    private Integer type;

    private String desc;

    private String name;

    private static Map<Integer, EntityTypeEnum> entityTypeEnumMap = new HashMap<>();

    private EntityTypeEnum(Integer type, String desc, String name) {
        this.type = type;
        this.desc = desc;
        this.name = name;
    }

    static {
        for(EntityTypeEnum temp : EntityTypeEnum.values()) {
            entityTypeEnumMap.put(temp.getType(), temp);
        }
    }

    public static EntityTypeEnum getEntityTypeEnumMap(Integer entityType) {
        return entityTypeEnumMap.getOrDefault(entityType, null);
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
