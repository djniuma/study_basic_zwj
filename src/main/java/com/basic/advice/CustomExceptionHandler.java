package com.basic.advice;

import com.basic.common.HttpResult;
import com.basic.error.HttpException;
import com.basic.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public HttpResult exceptionHandler(Exception e) {
        logger.info("捕获到未知异常");
        e.printStackTrace();
        return HttpResult.fail();
    }

    // 作业2
    @ExceptionHandler(value = HttpException.class)
    public HttpResult httpExceptionHandler(HttpException e) {
        logger.info("捕获到网络异常");
        e.printStackTrace();
        return HttpResult.fail();
    }
}
