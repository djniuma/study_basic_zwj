package com.basic.message.handle;

import com.basic.dto.CommentDTO;
import com.basic.message.AsyncMessageDTO;

import com.basic.message.MessageManage;
import com.basic.message.MessageTypeEnum;
import com.basic.message.dto.CommentMessageDTO;
import com.basic.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommentMessageHandle implements MessageManage {

    final static Logger logger = LoggerFactory.getLogger(CommentMessageHandle.class);


    @Override
    public void dealMessage(AsyncMessageDTO asyncMessageDTO) {
        logger.info("开始处理评论产生的消息:{}", JsonUtils.objectToJson(asyncMessageDTO));

        String message = asyncMessageDTO.getMessage();

        // 保证DTO的责任单一原则, 防止被其他人篡改
        CommentMessageDTO commentMessageDTO = JsonUtils.jsonToPojo(message, CommentMessageDTO.class);



        /**
         * TODO 1.判断是否是对动态产生评论, 动态的评论数量增加; 2.判断是否是对评论产生回复, 如果是, 需要通知被回复的人
         * TODO 作业: 1.设置消息表  2.设计消息相关的接口  3.将当前消息处理
         */
    }

    @Override
    public String getMessageType() {

        return MessageTypeEnum.COMMENT_MESSAGE.getType();
    }
}
