package com.basic.message;

import com.basic.service.DynamicService;
import com.basic.util.JedisUtil;
import com.basic.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MessageConsumer {

    ExecutorService executorService = Executors.newCachedThreadPool();

    final static Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private List<MessageManage> messageManages;

    @PostConstruct
    public void consumerMessage(){
        System.out.println("此项目启动, 被注解的方法, 没有执行");
        executorService.submit(() -> dealAsyncListMessage());
    }


    public void dealAsyncListMessage(){
        while(true){
            if (jedisUtil.llen(MessageContant.ASYNC_LIST) > 0){
                String message = jedisUtil.rpop(MessageContant.ASYNC_LIST);
                logger.info("开始消费消息:{}",message);
                try {
                    MessageDTO messageDTO = JsonUtils.jsonToPojo(message, MessageDTO.class);
                    if (ObjectUtils.isEmpty(messageDTO)) {
                        continue;
                    }
                    String type = messageDTO.getType();
                    messageManages.stream()
                            .filter(item -> item.getMessageType().equals(type))
                            .findFirst()
                            .get()
                            .dealMessage(messageDTO);
                } catch (Exception e){
                    logger.info("消费处理发生异常:{}",message);
                    e.printStackTrace();
                }
            }
        }
    }

}
