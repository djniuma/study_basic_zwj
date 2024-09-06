package com.basic.vo;

public class VoteOptionVO {

    private String id;

    /**
     * 选项顺序
     */
    private Integer optionOrder;

    /**
     * 投票的ID
     */
    private String voteId;

    /**
     * 投票选项的内容
     */
    private String content;

    /**
     * 当前选项投票的数量
     */
    private Integer voteCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(Integer optionOrder) {
        this.optionOrder = optionOrder;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
