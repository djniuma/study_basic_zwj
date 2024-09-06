package com.basic.controller;

import com.basic.common.HttpResult;
import com.basic.contant.TokenContant;
import com.basic.param.LoginParam;
import com.basic.param.RegisterParam;
import com.basic.result.RegisterResult;
import com.basic.service.TokenService;
import com.basic.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("token")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public HttpResult register(@RequestBody RegisterParam param, HttpServletResponse response){

        HttpResult result = tokenService.register(param);

        if(result.getData() != null){
            Cookie cookie = new Cookie("ticket",result.getData().toString());
            // 用常量类来代替数字，增加可读性
            cookie.setMaxAge(TokenContant.ONE_DAY_SECOND);
            cookie.setPath("/");
            response.addCookie(cookie);
            return HttpResult.ok();
        }

        return result;
    }

    @PostMapping("/login")
    public HttpResult login(@RequestBody LoginParam param, HttpServletResponse response){
        HttpResult result = tokenService.login(param);

        if(result.getData() != null){
            Cookie cookie = new Cookie("ticket",result.getData().toString());
            // 用常量类来代替数字，增加可读性
            cookie.setMaxAge(TokenContant.ONE_DAY_SECOND);
            cookie.setPath("/");
            response.addCookie(cookie);
            return HttpResult.ok();
        }

        return result;
    }

    @PostMapping("/logout")
    public HttpResult logout(){

        return tokenService.logout();
    }

}
