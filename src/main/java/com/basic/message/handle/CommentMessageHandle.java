package com.basic.message.handle;

import com.basic.dto.CommentDTO;
import com.basic.dto.DynamicDTO;
import com.basic.dto.InformationDTO;
import com.basic.enums.EntityTypeEnum;
import com.basic.mapper.CommentMapper;
import com.basic.mapper.DynamicMapper;
import com.basic.mapper.InformationMapper;
import com.basic.message.AsyncMessageDTO;

import com.basic.message.MessageManage;
import com.basic.message.MessageTypeEnum;
import com.basic.message.dto.CommentMessageDTO;
import com.basic.util.CommonUtil;
import com.basic.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentMessageHandle implements MessageManage {

    final static Logger logger = LoggerFactory.getLogger(CommentMessageHandle.class);

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private DynamicMapper dynamicMapper;

    @Autowired
    private InformationMapper informationMapper;


    @Override
    public void dealMessage(AsyncMessageDTO asyncMessageDTO) {
        logger.info("开始处理评论产生的消息:{}", JsonUtils.objectToJson(asyncMessageDTO));

        String message = asyncMessageDTO.getMessage();

        // 保证DTO的责任单一原则, 防止被其他人篡改
        CommentMessageDTO commentMessageDTO = JsonUtils.jsonToPojo(message, CommentMessageDTO.class);

        // 获取评论主体
        CommentDTO commentDTO = commentMapper.queryCommentDTOById(commentMessageDTO.getId());

        /**
         * TODO 1.判断是否是对动态产生评论, 动态的评论数量增加; 2.判断是否是对评论产生回复, 如果是, 需要通知被回复的人
         * TODO 作业: 1.设置消息表  2.设计消息相关的接口  3.将当前消息处理
         */

        DynamicDTO dynamicDTO = dynamicMapper.queryDynamicById(commentDTO.getEntityId());
        dynamicDTO.setCommentCount(dynamicDTO.getCommentCount() + 1);
        dynamicMapper.updateDynamic(dynamicDTO);

        Integer entityType = commentDTO.getEntityType();

        if(EntityTypeEnum.DYNAMIC.getType().equals(entityType)) {
            InformationDTO informationDTO = new InformationDTO();
            informationDTO.setId(CommonUtil.createUUID());
            informationDTO.setSendUserId(commentDTO.getUserId());
            informationDTO.setTakeUserId(dynamicDTO.getUserId());
            informationDTO.setEntityId(dynamicDTO.getId());
            informationDTO.setEntityType(EntityTypeEnum.DYNAMIC.getType());
            informationDTO.setContent("评论内容: " + commentDTO.getContent());
            informationDTO.setSendDate(new Date());

            informationMapper.addInformationDTO(informationDTO);
        }

        if(EntityTypeEnum.COMMENT.getType().equals(entityType)) {
            CommentDTO commentDTO2 = commentMapper.queryCommentDTOById(commentDTO.getEntityId());

            InformationDTO informationDTO = new InformationDTO();
            informationDTO.setId(CommonUtil.createUUID());
            informationDTO.setSendUserId(commentDTO.getUserId());
            informationDTO.setTakeUserId(commentDTO2.getUserId());
            informationDTO.setEntityId(commentDTO2.getId());
            informationDTO.setEntityType(EntityTypeEnum.COMMENT.getType());
            informationDTO.setContent("评论内容: " + commentDTO.getContent());
            informationDTO.setSendDate(new Date());
            informationMapper.addInformationDTO(informationDTO);


            informationDTO.setId(CommonUtil.createUUID());
            informationDTO.setSendUserId(commentDTO.getUserId());
            informationDTO.setTakeUserId(dynamicDTO.getUserId());
            informationDTO.setEntityId(dynamicDTO.getId());
            informationDTO.setEntityType(EntityTypeEnum.DYNAMIC.getType());
            informationDTO.setContent("评论内容: " + commentDTO.getContent());
            informationDTO.setSendDate(new Date());

            informationMapper.addInformationDTO(informationDTO);
        }

    }

    @Override
    public String getMessageType() {

        return MessageTypeEnum.COMMENT_MESSAGE.getType();
    }
}
