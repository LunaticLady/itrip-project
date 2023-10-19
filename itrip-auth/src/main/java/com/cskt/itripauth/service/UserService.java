package com.cskt.itripauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskt.entity.ItripUser;
import com.cskt.entity.condition.UserRegisterCondition;

public interface UserService extends IService<ItripUser> {

    boolean userRegister(UserRegisterCondition condition, String registerType); //用户注册方法

    boolean active(String userCode, String code); //激活邮箱账号

    boolean validatePhone(String phoneNum, String code); //激活手机账号

    void getActiveCode(String userCode); //获取激活码
}
