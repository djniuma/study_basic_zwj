package com.basic.service;

import com.basic.mapper.TestMapper;
import com.basic.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public String addTestContent (String content){
        String id = CommonUtil.createUUID();
        testMapper.addTestContent(id, content);
        return id;
    }


    public String testMysqlQuery(String id) {
        return testMapper.queryTestContentById(id);
    }
}
