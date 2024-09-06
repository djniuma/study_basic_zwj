package com.basic.enums;

public enum LoginTicketStatusEnum {

    NORMAL(0, "有效", "NORMAL"),

    EXPRIED(1,"无效","EXPRIED");

    private Integer status;

    private String desc;

    private String name;

    private LoginTicketStatusEnum(Integer status, String desc, String name) {
        this.status = status;
        this.desc = desc;
        this.name = name;
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

