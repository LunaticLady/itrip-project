package com.cskt.itripauth.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.cskt.common.config.AliSmsConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 短信验证码生成类
 */
@Service
public class AliSmsService {

    @Resource
    private AliSmsConfig aliSmsConfig;

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @param code  验证码
     * @return
     */
    public void sendSms(String phone, String code) {
        Config config = new Config()
                // 这里修改为我们上面生成自己的AccessKey ID
                .setAccessKeyId(aliSmsConfig.getAccessKeyId())
                // 这里修改为我们上面生成自己的AccessKey Secret
                .setAccessKeySecret(aliSmsConfig.getAccessKeySecret());
        // 访问的域名
        config.endpoint = aliSmsConfig.getEndpoint();
        try {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName(aliSmsConfig.getSignName()) // 短信签名
                    .setTemplateCode(aliSmsConfig.getTemplateCode())// 短信模板
                    .setPhoneNumbers(phone) // 接受短信的手机号码
                    .setTemplateParam("{\"code\":" + code + "}");// 短信验证码
            //发送短信
            client.sendSms(sendSmsRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("---------------| 手机号:{" + phone + "},验证码:{" + code + "} |---------------");
    }
}
