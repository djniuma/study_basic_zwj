package com.basic.message;

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
                    AsyncMessageDTO asyncMessageDTO = JsonUtils.jsonToPojo(message, AsyncMessageDTO.class);
                    if (ObjectUtils.isEmpty(asyncMessageDTO)) {
                        continue;
                    }
                    String type = asyncMessageDTO.getType();
                    messageManages.stream()
                            .filter(item -> item.getMessageType().equals(type))
                            .findFirst()
                            .get()
                            .dealMessage(asyncMessageDTO);
                } catch (Exception e){
                    // 通过日志打印异常信息，比栈打印消耗资源更少，性能更好
                    logger.info("消费处理发生异常:{}, 异常信息: {}", message, JsonUtils.objectToJson(e));
//                    e.printStackTrace();
                }
                System.out.println("消费消息: " + message);
            }
        }
    }

}
