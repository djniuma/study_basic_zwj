package com.basic.model;

import java.util.Date;

/**
 * 动态
 */
public class DynamicModel {

    /**
     * 动态ID
     */
    private String id;

    /**
     * 动态标题
     */
    private String title;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 动态状态 {@link com.basic.enums.DynamicStatusEnum}
     */
    private Integer status;

    /**
     * 动态类型 {@link com.basic.enums.DynamicTypeEnum}
     */
    private Integer type;

    /**
     * 图片数组
     */
    private String imgArray;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 删除时间
     */
    private Date deleteDate;

    /**
     * 扩展内容
     */
    private String extendContent;
}
