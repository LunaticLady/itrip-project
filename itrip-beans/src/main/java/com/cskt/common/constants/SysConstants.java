package com.cskt.common.constants;

/**
 * 系统常量类
 */
public class SysConstants {
    /* 用户类型常量 */
    public static class UserType{
        public static final Integer REGISTRATION = 0; //自注册
        public static final Integer WE_CHAT_LOGIN = 1; //微信
        public static final Integer QQ_LOGIN = 2; //QQ
        public static final Integer WEI_BO_LOGIN = 3; //微博
    }
    public static class UserActiveStatus{
        public static final Integer NOT_ACTIVE = 0; //未激活
        public static final Integer IS_ACTIVE = 1; //已激活
    }


}
