package com.cskt.entity.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户注册条件实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterCondition implements Serializable {
    private String userCode; //用户账号
    private String userName; //用户昵称
    private String userPassword; //用户密码
}
