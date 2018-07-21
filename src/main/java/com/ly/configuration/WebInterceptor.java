package com.ly.configuration;


import com.alibaba.fastjson.JSON;
import com.ly.Global;
import com.ly.helper.ErrorCode;
import com.ly.helper.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.ly.service.JwtCheckService;

public class WebInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Autowired
//    private JwtCheckService jwtCheckService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            this.rtnResponse(response);
            return false;
        } else {
            Long userId = 1L;// jwtCheckService.getUserId(token);
            if (userId > 0){
                request.setAttribute(Global.USER_ID,userId);
            }else{
                this.rtnResponse(response);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    private void rtnResponse(HttpServletResponse response) throws Exception{
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String rtnStr = JSON.toJSONString(new Result(ErrorCode.SESSION_EXPIRE));
        response.getOutputStream().write(rtnStr.getBytes());
        response.setStatus(HttpStatus.OK.value());
    }

}
