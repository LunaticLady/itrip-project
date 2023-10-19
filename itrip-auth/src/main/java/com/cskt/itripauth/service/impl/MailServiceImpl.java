package com.cskt.itripauth.service.impl;

import com.cskt.common.constants.ErrorCodeEnum;
import com.cskt.common.exception.ServiceException;
import com.cskt.itripauth.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MailServiceImpl implements MailService {
    private final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailSender; //发送者

    @Override
    public void sendActivationEmail(String mailTo, String activationCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailSender);
            mailMessage.setTo(mailTo);
            mailMessage.setSubject("爱旅行");
            mailMessage.setText("您的激活码为: " + activationCode);
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            log.error("调用邮件服务失败，{}，{}", e.getMessage(), e);
            throw new ServiceException(ErrorCodeEnum.ERROR_CALLING_THIRD_PARTY_SERVICE);
        }
    }
}
