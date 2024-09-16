package com.basic.controller;

import com.basic.common.HttpResult;
import com.basic.param.CommentParam;
import com.basic.param.PublishDynamicParam;
import com.basic.param.QueryDynamicPageParam;
import com.basic.param.TakeVoteParam;
import com.basic.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dynamic")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    @PostMapping("/publish")
    public HttpResult publishDynamic(@RequestBody PublishDynamicParam param) {

        return dynamicService.publishDynamic(param);
    }

    @PostMapping("/query/page")
    public HttpResult queryDynamicPage(@RequestBody QueryDynamicPageParam param) {

        return dynamicService.queryDynamicPage(param);
    }

    @PostMapping("/comment")
    public HttpResult comment(@RequestBody CommentParam param) {

        return dynamicService.comment(param);
    }

    @PostMapping("/vote")
    public HttpResult vote(@RequestBody TakeVoteParam param) {

        return dynamicService.vote(param);
    }

    /**
     * TODO 1、评论分页查询
     * TODO 2、消息通知
     * TODO 2、消息分页
     * TODO 4、个人主页展示---个人信息，个人的动态，点赞或者收藏的动态
     * TODO 5、敏感词的过滤----前缀树算法
     * TODO 6、删除动态/评论
     * TODO 7、添加动态的话题
     * TODO 8、通过redis搞一个动态热度的排行榜，热度的规则：点赞的权重 + 评论的权重 + 收藏的权重
     * TODO 9、给用户搞一个积分，做签到，积分兑换商品
     */

    @PostMapping("/comment/page")
    public HttpResult queryCommentPage() {

        return null;
    }

}
