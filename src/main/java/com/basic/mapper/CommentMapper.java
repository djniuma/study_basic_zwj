package com.basic.mapper;

import com.basic.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {


    public void addCommentDTO(CommentDTO commentDTO);

    CommentDTO queryCommentDTOById(@Param("id") String id);
}
