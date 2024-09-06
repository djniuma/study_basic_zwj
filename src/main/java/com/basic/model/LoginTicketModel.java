package com.basic.model;

import com.basic.enums.LoginTicketStatusEnum;

import java.util.Date;

public class LoginTicketModel {

    private String id;

    private String userId;

    private String ticket;

    private Date expiredDate;

    /**
     * {@link LoginTicketStatusEnum}
     */
    private Integer status;
}
