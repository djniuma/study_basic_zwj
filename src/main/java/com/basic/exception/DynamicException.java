package com.basic.exception;

import com.basic.error.ExceptionCode;

public enum DynamicException implements ExceptionCode {

    DYNAMIC_TITLE_IS_NOT_NULL(200001, "动态标题不能为空", "DYNAMIC_TITLE_IS_NOT_NULL"),

    DYNAMIC_CONTENT_IS_NOT_NULL(200002, "动态内容不能为空", "DYNAMIC_CONTENT_IS_NOT_NULL"),

    DYNAMIC_TYPE_IS_NOT_EXIST(200003, "动态类型不存在", "DYNAMIC_TYPE_IS_NOT_EXIST"),

    DYNAMIC_NOW_PAGE_IS_NOT_NULL(200004, "当前页不能为空", "DYNAMIC_NOW_PAGE_IS_NOT_NULL"),

    DYNAMIC_NOW_PAGE_IS_NOT_LESS_ONE(200005, "当前页不能小于1", "DYNAMIC_NOW_PAGE_IS_NOT_LESS_ONE"),

    DYNAMIC_PAGE_SIZE_IS_NOT_NULL(200006, "当前页大小不能为空", "DYNAMIC_PAGE_SIZE_IS_NOT_NULL"),

    DYNAMIC_PAGE_SIZE_IS_NOT_LESS_ONE(200007, "当前页大小不能小于1", "DYNAMIC_PAGE_SIZE_IS_NOT_LESS_ONE"),

    DYNAMIC_COUNT_IS_NULL(200008, "当前条件查询数据量为0", "DYNAMIC_COUNT_IS_NULL"),

    DYNAMIC_NOW_PAGE_DATE_IS_NULL(200009, "当前页没有数据", "DYNAMIC_NOW_PAGE_DATE_IS_NULL"),

    PUNCHCARDDATE_IS_NOT_NULL(200010, "打卡时间不能为空", "PUNCHCARDDATE_IS_NOT_NULL"),

    PUNCHCARDDATE_IS_ERROR(200011, "打卡时间错误", "PUNCHCARDDATE_IS_ERROR"),

    PUNCHCARDCONTENT_IS_NOT_NULL(200012, "打卡内容不能为空", "PUNCHCARDCONTENT_IS_NOT_NULL"),

    VOTEPARAM_IS_NOT_NULL(200013, "投票参数不能为空", "VOTEPARAM_IS_NOT_NULL"),

    PUNCHCARDPARAMA_IS_NOT_NULL(200014, "打卡参数不能为空", "PUNCHCARDPARAM_IS_NOT_NULL"),

    VOTETITLE_IS_NOT_NULL(200015, "投票标题不能为空", "VOTETITLE_IS_NOT_NULL"),

    VOTE_OPTION_IS_NOT_NULL(200016, "投票选项不能为空", "VOTE_OPTION_IS_NOT_NULL"),

    VOTE_OPTION_IS_NOT_LESS_TWO(200017, "投票选项不能小于2", "VOTE_OPTION_IS_NOT_LESS_TWO"),

    VOTE_OPTION_CONTENT_IS_NOT_NULL(200018, "投票选项内容不能为空", "VOTE_OPTION_CONTENT_IS_NOT_NULL"),
    ;

    private Integer code;

    private String message;

    private String name;

    private DynamicException(Integer code, String message, String name) {
        this.code = code;
        this.message = message;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
