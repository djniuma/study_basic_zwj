package com.basic.vo;

import com.basic.model.VoteOptionModel;

import java.util.List;

/**
 * 投票
 */
public class VoteVO {

    private String id;

    /**
     * 动态Id
     */
    private String dynamicId;

    /**
     * 投票标题
     */
    private String voteTitle;

    /**
     * 参与投票的人数
     */
    private Integer voteTakeCount;

    /**
     * 选项集合
     */
    private List<VoteOptionVO> optionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public Integer getVoteTakeCount() {
        return voteTakeCount;
    }

    public void setVoteTakeCount(Integer voteTakeCount) {
        this.voteTakeCount = voteTakeCount;
    }

    public List<VoteOptionVO> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<VoteOptionVO> optionList) {
        this.optionList = optionList;
    }
}
