package com.basic.dto;

import java.util.Date;

public class CommentDTO {

    private String id;

    private String userId;

    /**
     * {@link com.basic.enums.EntityTypeEnum}
     */
    private Integer entity_type;

    private String entity_id;

    private String content;

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(Integer entity_type) {
        this.entity_type = entity_type;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
