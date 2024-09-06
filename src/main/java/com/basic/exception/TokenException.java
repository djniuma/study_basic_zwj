package com.basic.exception;

import com.basic.error.ExceptionCode;

public enum TokenException implements ExceptionCode {

    TOKEN_USERNAME_IS_NOT_NULL(100001, "用户名不能为空", "TOKEN_USERNAME_IS_NOT_NULL"),

    TOKEN_PASSWORD_IS_NOT_NULL(100002, "密码不能为空", "TOKEN_PASSWORD_IS_NOT_NULL"),

    TONEN_USER_IS_NOT_EXIST(100003, "用户不存在", "TONEN_USER_IS_NOT_EXIST"),

    TOKEN_PASSWORD_IS_ERROR(100004, "密码错误", "TOKEN_PASSWORD_IS_ERROR"),
    ;

    private Integer code;

    private String message;

    private String name;

    private TokenException(Integer code, String message, String name) {
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
