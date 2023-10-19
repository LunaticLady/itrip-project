package com.cskt.common.handler;

import com.alibaba.fastjson.JSON;
import com.cskt.common.constants.ErrorCodeEnum;
import com.cskt.common.vo.ReturnResult;
import com.cskt.common.vo.TokenVo;
import com.cskt.itripauth.security.ItripUserDetails;
import com.cskt.util.JWTUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * 认证处理器
 */
@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {
    private final String authedUserKeyPre = "itrip:authed:";//已认证的用户存入redis前缀
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 认证成功
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取认证用户信息
        ItripUserDetails userDetails = (ItripUserDetails) authentication.getPrincipal();
        //生成包含用户名称的token
        String token = JWTUtils.sign(userDetails.getUsername());
        long genTime = System.currentTimeMillis(); //token生成时间
        long expTime = genTime + 30 * 60 * 1000; //token过期时间
        //将认证用户的信息存入redis
        stringRedisTemplate.opsForValue().set(authedUserKeyPre.concat(token), JSON.toJSONString(userDetails.getUser()),
                30, TimeUnit.MINUTES);
        //创建token响应对象
        TokenVo tokenVo = new TokenVo(token, expTime, genTime);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(ReturnResult.ok(tokenVo)));
        writer.flush();
        writer.close();
    }

    /**
     * 认证失败
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(ReturnResult.error(ErrorCodeEnum.AUTH_AUTHENTICATION_FAILED)));
        writer.flush();
        writer.close();
    }


    /**
     * 退出登录
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(ReturnResult.error(ErrorCodeEnum.AUTH_LOGOUT_SUCCESS)));
        writer.flush();
        writer.close();
    }

}
