package com.cskt.common.config;

import com.alibaba.fastjson.JSON;
import com.cskt.common.constants.ErrorCodeEnum;
import com.cskt.common.filter.Loginfilter;
import com.cskt.common.vo.ReturnResult;
import com.cskt.common.vo.TokenVo;
import com.cskt.itripauth.security.ItripUserDetails;
import com.cskt.itripauth.service.ItripAuthUserDetailsService;
import com.cskt.util.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Author:长沙科泰梦想学院
 * @Mission:打造演说·创客型跨境电商与互联网人才
 * @Author:半夜Code
 * @Description:
 **/

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String authedUserKeyPre = "itrip:authed:";//已认证的用户存入redis前缀
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ItripAuthUserDetailsService userDetailsService;


    @Resource
    private DataSource dataSource;


    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
      // 指定查询数据库用户信息的servic类
        auth.userDetailsService(userDetailsService);

    }

    @Bean
    public AuthenticationManager authenticationManagerBean () throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public Loginfilter loginfilter () throws Exception {
        Loginfilter filter = new Loginfilter();
        filter.setFilterProcessesUrl("/dolgin");
        filter.setUsernameParameter("name");
        filter.setPasswordParameter("password");

        filter.setAuthenticationManager(authenticationManagerBean());
        // 设置认证成功的处理  方式1： 自定义一个类去实现AuthenticationSuccessHandler
        filter.setAuthenticationSuccessHandler((req,resp,authentication)->{
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
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write(JSON.toJSONString(ReturnResult.ok(tokenVo)));
            writer.flush();
            writer.close();
        });
        filter.setAuthenticationFailureHandler((req,resp,authentication)->{
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write(JSON.toJSONString(ReturnResult.error(ErrorCodeEnum.AUTH_AUTHENTICATION_FAILED)));
            writer.flush();
            writer.close();
        });
        return filter;


    }


    @Override
    protected void configure (HttpSecurity http) throws Exception {
        // 所有请求必须经过认证
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                        .and().exceptionHandling()
                // 访问需要认证的资源时但是未认证的处理
                .authenticationEntryPoint((req,resp,authentication)->{
                    // 返回一个json信息
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter writer = resp.getWriter();
                    writer.write(JSON.toJSONString(ReturnResult.error(ErrorCodeEnum.AUTH_NOT_LOGIN)));
                    writer.flush();
                    writer.close();


                });

        // before: 在指定的过滤器之前 添加新的过滤器
        // at ： 在指定的位置替换
        // after:  在指定的过滤器之后 添加新的过滤器
        // UsernamePasswordAuthenticationFilter
        http.addFilterAt(loginfilter(), UsernamePasswordAuthenticationFilter.class);




        //禁用csrf(伪造跨域)防护
        http.csrf().disable();


    }

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository () {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(dataSource);
        //启动时创建一张表，第一次启动时创建，第二次需要注释掉，否则报错
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main (String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("12345");
        System.out.println(encode);
    }
}
