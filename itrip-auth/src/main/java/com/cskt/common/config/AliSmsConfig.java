package com.cskt.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信 配置类
 */
@Configuration
@ConfigurationProperties(prefix = "ali.sms")
@Data
public class AliSmsConfig {
   private String accessKeyId; //密钥id
   private String accessKeySecret; //密钥
   private String endpoint; //域名
   private String signName; //签名
   private String templateCode; //模板编号
}
