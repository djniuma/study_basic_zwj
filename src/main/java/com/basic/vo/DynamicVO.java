package com.basic.vo;

import java.util.Date;
import java.util.List;

/**
 * 动态
 */
public class DynamicVO {

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
     * 用户名
     */
    private String username;

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
    private List<String> imgs;

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
    private Object extendContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Object getExtendContent() {
        return extendContent;
    }

    public void setExtendContent(Object extendContent) {
        this.extendContent = extendContent;
    }
}
