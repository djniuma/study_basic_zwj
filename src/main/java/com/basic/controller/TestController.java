package com.basic.controller;

import com.basic.common.HttpResult;
import com.basic.message.MessageContant;
import com.basic.service.TestService;
import com.basic.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TestService testService;

    final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test1")
    public String test(){
        return "test";
    }

    @PostMapping("/test/mysql")
    public String testMysql(@RequestParam("content") String content){
        return "success" + " + " + testService.addTestContent(content);
    }

    @GetMapping("/test/mysql/{id}")
    public String testMysqlQuery(@PathVariable ("id") String id){
        logger.info("查询test的content，通过id:{}",id);
        return testService.testMysqlQuery(id);
    }

    @GetMapping("/test")
    public String testMysqlQuery1(@RequestParam("id") String id){
        return testService.testMysqlQuery(id);
    }

    @PostMapping(value = "/test3", consumes = "application/x-www-form-urlencoded")
    public String testUrlEncoded(@RequestParam("name") String name,
                                 @RequestParam("tel") String tel) {

        return "name : " + name + "; tel : " + tel;
    }

    @Autowired
    JedisUtil jedisUtil;

    @PostMapping("/send")
    public HttpResult sendMessage(@RequestParam("message") String message) {
        jedisUtil.lpush(MessageContant.ASYNC_LIST, message);
        return HttpResult.ok();
    }
}
