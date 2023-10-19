package com.cskt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author mo
 * @Description: Jwt工具类，用于生成和校验jwt
 * @date 2020-09-22 15:01
 */
public class JWTUtils {
    /**
     * 过期时间为半小时
     */
    private static final long EXPIRE_TIME = 30 * 60 * 1000;
    /**
     * 加密时的密钥，不能泄露
     */
    private static final String secret = "15D89EE0-65C6-81EC-614A-CD1B30F6D86D";

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            // 加密方式必须与生成token时的加密方式一致
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token中的信息无需secret解密也能获得
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").toString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成JWTToken
     *
     * @param username
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String sign(String username) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC512(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}