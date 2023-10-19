package com.cskt.common.filter;

import com.cskt.entity.ItripUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author:长沙科泰梦想学院
 * @Mission:打造演说·创客型跨境电商与互联网人才
 * @Author:半夜Code
 * @Description:
 **/
public class Loginfilter extends UsernamePasswordAuthenticationFilter {

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            //  异步发送过来的登录信息

            ServletInputStream stream = request.getInputStream();
            Map<String, String> userMap = new ObjectMapper().readValue(stream, Map.class);


            String username = userMap.get(getUsernameParameter()) != null ? userMap.get(getUsernameParameter()).trim() : "";
            String password = userMap.get(getPasswordParameter()) != null ? userMap.get(getPasswordParameter()).trim() : "";

            System.out.println("userName:" + username + "  --------------   password:" + password);

            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        }


        return super.attemptAuthentication(request, response);
    }
}
