package com.cskt.common.config;

import com.cskt.common.filter.JwtAuthenticationFilter;
import com.cskt.common.handler.AuthenticationHandler;
import com.cskt.itripauth.service.ItripAuthUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * security 配置类
 */
//@Configuration
//@EnableWebSecurity //启用当前security配置 禁用默认的配置
public class SecurityConfiguration {
    @Resource
    private ItripAuthUserDetailsService userDetailsService;

    @Resource
    private AuthenticationHandler authenticationHandler;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private DataSource dataSource;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //禁用csrf(伪造跨域)防护
        http.csrf().disable();
        //允许iframe加载页面
        http.headers().frameOptions().sameOrigin();
        // 加在用户名密码过滤器的前面
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.formLogin() //开启表单验证
                .loginPage("/toLoginPage") //没有认证时，跳转到认证页面
                .loginProcessingUrl("/api/dolgin") //登录请求地址，表单请求地址
                .usernameParameter("account") //账号元素名称
                .passwordParameter("password") //密码元素名称
                .successForwardUrl("/") //认证成功跳转路径
                .successHandler(authenticationHandler) //认证成功处理
                .failureHandler(authenticationHandler) //认证失败处理
                .and()
                .logout() // 退出登录
//                .logoutUrl("/logout") //设置退出的url
                .logoutSuccessHandler(authenticationHandler) //退出成功处理
                .and()
                .authorizeRequests() //认证所有请求
                .regexMatchers("/auth/api/ckusr", "/auth/api/dologin",
                        "/auth/api/doregister", "/auth/api/activate", "/auth/api/validatephone").permitAll() //匹配符合规则的放行
                .anyRequest().authenticated() //表示所有的请求都必须认证才能访问
                .and()
                .userDetailsService(userDetailsService) //绑定自定义认证Service
                .build(); //返回对象
    }

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(dataSource);
        //启动时创建一张表，第一次启动时创建，第二次需要注释掉，否则报错
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
