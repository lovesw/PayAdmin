package com.pay.data.utils;

import cn.hutool.core.codec.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @createTime: 2018/1/3
 * @author: HingLo
 * @description: 加密工具
 */
public class JwtUtil {
    /**
     * 定义key
     */
    private final static String stringKey = "JwUtil7786df7fc3a34e26a61c034d5ec8245d";
    /**
     * 定义有效时间
     * 设置有效时间为3 hours
     */
    private final static int ttlMillis = 1000 * 60 * 10 * 3;


    /**
     * 由字符串生成加密key
     *
     * @return 加密key
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(stringKey);
        return new SecretKeySpec(encodedKey, "AES");
    }


    /**
     * 创建jwt
     *
     * @param id      id
     * @param subject 加密主题的内容
     * @return token字符串
     */
    public static String createJWT(String id, String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param token 解密的token
     * @return Claims 对象
     */
    public static Claims parseJWT(String token) {
        SecretKey key = generalKey();
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();
    }
}
