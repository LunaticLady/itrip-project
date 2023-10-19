package com.cskt.itripauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskt.common.constants.ErrorCodeEnum;
import com.cskt.common.constants.SysConstants;
import com.cskt.common.exception.ServiceException;
import com.cskt.entity.ItripUser;
import com.cskt.entity.condition.UserRegisterCondition;
import com.cskt.itripauth.service.AliSmsService;
import com.cskt.itripauth.service.MailService;
import com.cskt.itripauth.service.UserService;
import com.cskt.mapper.ItripUserMapper;
import com.cskt.util.MD5Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<ItripUserMapper, ItripUser> implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private String activeCodeKeyPre = "itrip:active:"; //激活码存入redis的key值前缀
    private String checkCodeKeyPre = "itrip:active:"; //验证码存入redis的key值前缀
    @Value(value = "${enable.send.email}")
    private boolean enableSendEmail; //是否开启邮件发送
    @Value(value = "${enable.send.sms}")
    private boolean enableSendSms; //是否开启邮件发送
    @Resource
    private MailService mailService;
    @Resource
    private AliSmsService smsService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean userRegister(UserRegisterCondition condition, String registerType) {
        try {
            //判断用户是否存在
            QueryWrapper<ItripUser> qw = new QueryWrapper<>();
            qw.eq("user_code", condition.getUserCode());
            ItripUser user = this.getOne(qw);
            if (user != null) {
                throw new ServiceException(ErrorCodeEnum.AUTH_USER_ALREADY_EXISTS);
            }
            //实体转换
            user = new ItripUser();
            condition.setUserPassword(MD5Utils.getMd5(condition.getUserPassword(), 32));
            BeanUtils.copyProperties(condition, user);
            user.setUserType(SysConstants.UserType.REGISTRATION);//初始化默认注册用户类型
            this.save(user);//将信息存入数据库
            //选择注册方式
            switch (registerType) {
                case "email":
                    //生成激活码
                    String activationCode = MD5Utils.getMd5(String.valueOf(System.currentTimeMillis()), 32);
                    //判断是否发送激活码邮件
                    if (enableSendEmail) {
                        mailService.sendActivationEmail(condition.getUserCode(), activationCode);
                    }
                    //将激活码存入redis
                    stringRedisTemplate.opsForValue()
                            .set(activeCodeKeyPre + condition.getUserCode(), activationCode, 30, TimeUnit.MINUTES);
                    log.info("邮箱:{},激活码:{}", condition.getUserCode(), activationCode);
                    break;
                case "phone":
                    //生成验证码
                    String checkCode = String.valueOf(MD5Utils.getRandomCode());
                    //判断是否发送激活码邮件
                    if (enableSendSms) {
                        smsService.sendSms(condition.getUserCode(), checkCode);
                    }
                    //将激活码存入redis
                    stringRedisTemplate.opsForValue()
                            .set(checkCodeKeyPre + condition.getUserCode(), checkCode, 30, TimeUnit.MINUTES);
                    log.info("手机号:{},验证码:{}", condition.getUserCode(), checkCode);
                    break;
            }
        } catch (BeansException e) {
            throw new ServiceException(ErrorCodeEnum.SYSTEM_EXECUTION_ERROR);
        }
        return true;
    }

    @Override
    public boolean active( String userCode,  String code) {
        //激活码（redis）
        String activeCode = stringRedisTemplate.opsForValue().get(activeCodeKeyPre.concat(userCode));
        //判断redis中是否存在该用户注册用的激活码
        if (StringUtils.hasText(activeCode)) {
            //判断用户输入的激活码是否正确
            if (code.equals(activeCode)) {
                QueryWrapper<ItripUser> qw = new QueryWrapper<>();
                qw.eq("user_code", userCode);
                ItripUser user = this.getOne(qw);
                if (user != null) {
                    user.setActivated(SysConstants.UserActiveStatus.IS_ACTIVE);
                    user.setUserType(SysConstants.UserType.REGISTRATION);
                    user.setFlatId(user.getId());
                    this.updateById(user);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean validatePhone(String phoneNum,  String code) {
        //验证码（redis）
        String checkCode = stringRedisTemplate.opsForValue().get(checkCodeKeyPre.concat(phoneNum));
        //判断redis中是否存在该用户注册用的激活码
        if (StringUtils.hasText(checkCode)) {
            //判断用户输入的激活码是否正确
            if (code.equals(checkCode)) {
                QueryWrapper<ItripUser> qw = new QueryWrapper<>();
                qw.eq("user_code", phoneNum);
                ItripUser user = this.getOne(qw);
                if (user != null) {
                    user.setActivated(SysConstants.UserActiveStatus.IS_ACTIVE);
                    user.setUserType(SysConstants.UserType.REGISTRATION);
                    user.setFlatId(user.getId());
                    this.updateById(user);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getActiveCode(String userCode) {
        //查询用户是否存在并且激活状态为0（未激活）
        LambdaQueryWrapper<ItripUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ItripUser::getUserCode, userCode);
        lqw.eq(ItripUser::getActivated, 0);
        int count = this.count(lqw);
        //如果不存在,则抛出异常
        if (count <= 0) {
            throw new ServiceException(ErrorCodeEnum.AUTH_UNKNOWN);
        }
        //激活码（redis）
        String activationCode = stringRedisTemplate.opsForValue().get(activeCodeKeyPre + userCode);
        if (StringUtils.hasLength(activationCode)) {
            stringRedisTemplate.expire(activeCodeKeyPre + userCode, 30, TimeUnit.MINUTES);
        } else {
            activationCode = MD5Utils.getMd5(String.valueOf(System.currentTimeMillis()), 32);
            //将激活码存入redis
            stringRedisTemplate.opsForValue()
                    .set(activeCodeKeyPre + userCode, activationCode, 30, TimeUnit.MINUTES);
        }
        mailService.sendActivationEmail(userCode, activationCode);
    }
}
