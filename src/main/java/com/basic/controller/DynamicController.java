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
}
