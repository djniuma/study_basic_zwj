package com.basic.message;

import com.basic.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private JedisUtil jedisUtil;

    public void produceMessage(String message){
        jedisUtil.lpush(MessageContant.ASYNC_LIST,message);
    }
}
