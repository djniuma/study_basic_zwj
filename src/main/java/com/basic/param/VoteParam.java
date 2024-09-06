package com.basic.param;

import com.basic.model.VoteOptionModel;

import java.util.List;

/**
 * 投票
 */
public class VoteParam {

    /**
     * 投票标题
     */
    private String voteTitle;

    /**
     * 选项集合
     */
    private List<VoteOptionParam> optionList;

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public List<VoteOptionParam> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<VoteOptionParam> optionList) {
        this.optionList = optionList;
    }
}
