package com.basic.message;

public enum MessageTypeEnum {

    COMMENT_MESSAGE("COMMENT_MESSAGE", "评论产生的异步消息"),

    ;

    private String type;

    private String desc;

    private MessageTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
