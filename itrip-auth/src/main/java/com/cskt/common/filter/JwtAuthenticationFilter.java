package com.cskt.common.filter;

import com.cskt.common.vo.ReturnResult;
import com.cskt.entity.ItripUser;
import com.cskt.itripauth.security.ItripUserDetails;
import com.cskt.util.JWTUtils;
import com.cskt.itripauth.security.RequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * 请求过滤器
 * 用于拦截用户请求
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request = new RequestWrapper(request);
        //不需要拦截的url集合（允许放行的url）
        List<String> allowUrl = Arrays.asList("/auth/api/ckusr", "/auth/api/dologin",
                "/auth/api/doregister", "/auth/api/activate", "/auth/api/validatephone");
        String requestURI = request.getRequestURI();
        //若当前rul允许放行,则不再执行该过滤器,进入下一个过滤器
        if (allowUrl.contains(requestURI)) {
            filterChain.doFilter(request, response);
        }
        //获取请求头中的token
        String jwtToken = request.getHeader("token");
        //若token为空或token验证失败
        if (!StringUtils.hasLength(jwtToken) || !JWTUtils.verify(jwtToken)) {
            //生成一个http响应
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(writer, ReturnResult.error());
        }
        //从token中获取用户名
        String username = JWTUtils.getUsername(jwtToken);
        ItripUser user = new ItripUser();
        user.setUserName(username);
        //将用户信息存入authentication,方便后续校验
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(new ItripUserDetails(user), null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //将authentication存入ThreadLocal,方便后续获取用户信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
