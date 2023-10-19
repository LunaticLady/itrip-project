package com.cskt.itripauth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cskt.common.constants.ErrorCodeEnum;
import com.cskt.common.exception.ServiceException;
import com.cskt.entity.ItripUser;
import com.cskt.itripauth.security.ItripUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 用户信息处理类
 */
@Service
public class ItripAuthUserDetailsService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(ItripAuthUserDetailsService.class);

    @Resource
    private UserService userService;

    /**
     * 加载用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //若参数为空
        if (!StringUtils.hasLength(username)) {
            throw new ServiceException(ErrorCodeEnum.AUTH_PARAMETER_IS_EMPTY);
        }
        //根据用户名从数据库中获取用户信息
        LambdaQueryWrapper<ItripUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ItripUser::getUserCode, username);
        ItripUser user = userService.getOne(lqw);
        //若用户不存在
        if (user == null) {
            log.info("用户：【{}】不存在");
            throw new ServiceException(ErrorCodeEnum.AUTH_UNKNOWN);
        }
        return new ItripUserDetails(user);
    }
}
