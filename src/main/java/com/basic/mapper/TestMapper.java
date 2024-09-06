package com.basic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {

    public void addTestContent(@Param("id") String id, @Param("content") String content);

    public String queryTestContentById(@Param("id") String id);
}
