package com.cskt.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * token响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo implements Serializable {
    private String token; //认证凭证
    private Long expTime; //过期时间
    private Long genTime; //生成时间
}
