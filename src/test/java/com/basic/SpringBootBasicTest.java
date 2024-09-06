package com.basic;

import com.basic.param.RegisterParam;
import com.basic.service.TokenService;
import com.basic.util.CommonUtil;
import com.basic.util.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

//测试用例，类注解
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootBasicTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void testSalt(){
        System.out.println(CommonUtil.createUUID().substring(0, 6));
    }

    @Test
    public void testTokenService(){
        RegisterParam param = new RegisterParam();
        param.setUsername("user-3");
        param.setPassword("123456");
        tokenService.register(param);
    }

    @Autowired
    private JedisUtil jedisUtil;

    @Test
    public void testJedis(){
        jedisUtil.set("test", "100");
        System.out.println("第一次获取到key的值: " + jedisUtil.get("test"));
        jedisUtil.incr("test");
        System.out.println("第二次获取到key的值: " + jedisUtil.get("test"));
        jedisUtil.incrBy("test", 5L);
        System.out.println("第三次获取到key的值: " + jedisUtil.get("test"));
        jedisUtil.decr("test");
        System.out.println("第四次获取到key的值: " + jedisUtil.get("test"));
        jedisUtil.decrBy("test", 20L);
        System.out.println("第五次获取到key的值: " + jedisUtil.get("test"));
    }
}
