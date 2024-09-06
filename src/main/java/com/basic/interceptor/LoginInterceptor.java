package com.basic.interceptor;

import com.basic.common.HttpResult;
import com.basic.dto.LoginTicketDTO;
import com.basic.dto.UserDTO;
import com.basic.enums.LoginTicketStatusEnum;
import com.basic.error.CommonExceptionEnum;
import com.basic.local.HostHolder;
import com.basic.mapper.LoginTicketMapper;
import com.basic.mapper.UserMapper;
import com.basic.service.TokenService;
import com.basic.util.CommonUtil;
import com.basic.util.JsonUtils;
import com.basic.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 目标方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入拦截请求, url: {}", request.getRequestURL());
        String ticket = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ticket")) {
                ticket = cookie.getValue();
            }
        }
        if (StringUtils.isEmpty(ticket)) {
            response.setContentType("application/json;charset=utf-8");
            HttpResult result = HttpResult.generateHttpResult(CommonExceptionEnum.TOKEN_NOT_NULL);
            response.getWriter().println(JsonUtils.objectToJson(result));
            return false;
        }
        LoginTicketDTO loginTicketDTO = loginTicketMapper.queryTicketByTicket(ticket);

        if (loginTicketDTO == null ||
                loginTicketDTO.getStatus().equals(LoginTicketStatusEnum.EXPRIED.getStatus()) ||
                loginTicketDTO.getExpiredDate().getTime() < new Date().getTime()) {
            response.setContentType("application/json;charset=utf-8");
            HttpResult result = HttpResult.generateHttpResult(CommonExceptionEnum.TOKEN_NOT_NULL);
            response.getWriter().println(JsonUtils.objectToJson(result));
            return false;
        }
        String userId = loginTicketDTO.getUserId();
        UserDTO userDTO = userMapper.queryUserDTOById(userId);

        UserVO userVO = new UserVO();
        userVO.setId(userDTO.getId());
        userVO.setUsername(userDTO.getUsername());

        hostHolder.setUser(userVO);

        return true;
    }

    /**
     * 目标方法执行之后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 页面渲染后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
