package com.basic.service;

import com.basic.common.HttpResult;
import com.basic.contant.TokenContant;
import com.basic.dto.LoginTicketDTO;
import com.basic.dto.UserDTO;
import com.basic.enums.LoginTicketStatusEnum;
import com.basic.exception.TokenException;
import com.basic.local.HostHolder;
import com.basic.mapper.LoginTicketMapper;
import com.basic.mapper.UserMapper;
import com.basic.param.LoginParam;
import com.basic.param.RegisterParam;
import com.basic.util.CommonUtil;
import com.basic.util.JsonUtils;

import com.basic.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class TokenService {

    final static Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private HostHolder hostHolder;


    @Transactional(rollbackFor = Exception.class)  // 发生异常进行事务回滚
    public HttpResult register(RegisterParam param) {
        logger.info("开始注册,请求参数:{}", JsonUtils.objectToJson(param));
        String username = param.getUsername();
        if(StringUtils.isEmpty(username)) {
            logger.info("开始注册,用户名为空,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.TOKEN_USERNAME_IS_NOT_NULL);
        }
        String password = param.getPassword();
        if(StringUtils.isEmpty(password)) {
            logger.info("开始注册,密码为空,用户名不为空,请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(TokenException.TOKEN_PASSWORD_IS_NOT_NULL);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(CommonUtil.createUUID());
        userDTO.setUsername(username);
        String salt = CommonUtil.createUUID().substring(0, 6);
        userDTO.setSalt(salt);
        // 加了盐，MD5的反编译就破解不了密码
        userDTO.setPassword(CommonUtil.MD5(password + salt));
        try {
            userMapper.addUserDTO(userDTO);
            logger.info("开始注册, 用户添加成功,请求参数:{},userDTO:{}", JsonUtils.objectToJson(param), JsonUtils.objectToJson(userDTO));
            LoginTicketDTO loginTicketDTO = new LoginTicketDTO();
            loginTicketDTO.setId(CommonUtil.createUUID());
            loginTicketDTO.setUserId(userDTO.getId());
            loginTicketDTO.setTicket(CommonUtil.createUUID());
            long time = new Date().getTime() + TokenContant.DEFAULT_TICKET_EXPIRED_TIME;
            loginTicketDTO.setExpiredDate(new Date(time));
            loginTicketDTO.setStatus(LoginTicketStatusEnum.NORMAL.getStatus());
            loginTicketMapper.addLoginTicketDTO(loginTicketDTO);
            logger.info("开始注册, 用户令牌添加成功,请求参数:{},loginTicketDTO:{}", JsonUtils.objectToJson(param), JsonUtils.objectToJson(loginTicketDTO));
            return new HttpResult<>(loginTicketDTO.getTicket());
        } catch (Exception e) {
            logger.error("开始注册,发生异常,请求参数:{}", JsonUtils.objectToJson(param));
            e.printStackTrace();
            throw e;
        }
    }


    public HttpResult login(LoginParam param) {
        try{
            logger.info("开始登录,请求参数:{}", JsonUtils.objectToJson(param));
            String username = param.getUsername();
            if(StringUtils.isEmpty(username)) {
                logger.info("开始登录,用户名为空,请求参数:{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(TokenException.TOKEN_USERNAME_IS_NOT_NULL);
            }
            String password = param.getPassword();
            if(StringUtils.isEmpty(password)) {
                logger.info("开始登录,密码为空,用户名不为空,请求参数:{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(TokenException.TOKEN_PASSWORD_IS_NOT_NULL);
            }
            UserDTO userDTO = userMapper.queryUserDTO(username);
            if(ObjectUtils.isEmpty(userDTO)) {
                logger.info("开始登录,用户为空,请求参数:{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(TokenException.TONEN_USER_IS_NOT_EXIST);
            }
            if(!userDTO.getPassword().equals(CommonUtil.MD5(password + userDTO.getSalt()))) {
                logger.info("开始登录,密码错误,请求参数:{}", JsonUtils.objectToJson(param));
                return HttpResult.generateHttpResult(TokenException.TOKEN_PASSWORD_IS_ERROR);
            }
            logger.info("开始登录,用户名与密码正确,开始更新令牌,请求参数:{}", JsonUtils.objectToJson(param));
            LoginTicketDTO loginTicketDTO = loginTicketMapper.queryTicketByUserId(userDTO.getId());
            long time = new Date().getTime() + TokenContant.DEFAULT_TICKET_EXPIRED_TIME;
            loginTicketDTO.setExpiredDate(new Date(time));
            loginTicketDTO.setStatus(LoginTicketStatusEnum.NORMAL.getStatus());
            loginTicketMapper.updateLoginTicketDTO(loginTicketDTO);
            return new HttpResult(loginTicketDTO.getTicket());
        } catch (Exception e){
            logger.error("开始登录,发生异常,请求参数:{}", JsonUtils.objectToJson(param));
            e.printStackTrace();
            throw e;
        }
    }


    public HttpResult logout() {
        UserVO userVO = hostHolder.getUser();
        logger.info("开始退出,用户信息:{}", JsonUtils.objectToJson(userVO));
        LoginTicketDTO loginTicketDTO = loginTicketMapper.queryTicketByUserId(userVO.getId());
        loginTicketDTO.setStatus(LoginTicketStatusEnum.EXPRIED.getStatus());
        loginTicketMapper.updateLoginTicketDTO(loginTicketDTO);
        return HttpResult.ok();
    }
}
