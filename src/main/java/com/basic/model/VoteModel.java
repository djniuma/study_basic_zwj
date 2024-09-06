package com.basic.model;

import java.util.List;

/**
 * 投票
 */
public class VoteModel {

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
    private List<VoteOptionModel> optionList;

}
