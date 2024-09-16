package com.basic.mapper;

import com.basic.dto.DynamicDTO;
import com.basic.dto.DynamicPageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicMapper {

    void insertDynamic(DynamicDTO dynamicDTO);

    Integer queryDynamicCount();

    List<DynamicPageDTO> queryDynamicPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    DynamicDTO queryDynamicById(@Param("id") String id);

    Boolean queryDynamicIDById(@Param("id") String id);


    void updateDynamic(DynamicDTO dynamicDTO);
}
