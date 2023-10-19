package com.cskt.itripauth.service;


public interface MailService {
    /**
     * 发送激活邮件
     * @param mailTo 收件人邮箱
     * @param activationCode 激活码
     */
    void sendActivationEmail(String mailTo,String activationCode);
}
