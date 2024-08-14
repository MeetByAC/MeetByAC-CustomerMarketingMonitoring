package com.dta.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    /**
     * 秘钥
     */
//    private static final String signKey = "CMM";
    private static byte[] signKey = "CQ".getBytes(StandardCharsets.UTF_8);
    /**
     * 令牌有效时间，30分钟
     */
    private static final Long expire = 1000L * 60 * 30;

    /**
     * 生成Jwt令牌
     *
     * @param claims 信息键值对
     * @return
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.
                builder().
                signWith(SignatureAlgorithm.HS256, signKey).
                addClaims(claims).
                setExpiration(new Date(System.currentTimeMillis() + expire)).
                compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     *
     * @param jwt JWT令牌
     * @return
     */
    public static Claims parseJWT(String jwt) {

        Claims claims = Jwts.
                parser().
                setSigningKey(signKey).
                parseClaimsJws(extractJwtToken(jwt)).
                getBody();
        return claims;
    }

    /**
     * 提取jwt令牌，过滤令牌开头的Bearer
     *
     * @param authorizationHeader
     * @return
     */
    public static String extractJwtToken(String authorizationHeader) {
        String tokenPrefix = "Bearer ";
        if (authorizationHeader.startsWith(tokenPrefix)) {
            return authorizationHeader.substring(tokenPrefix.length());
        }
        return authorizationHeader; // 如果没有 "Bearer " 前缀，直接返回原始字符串
    }
}
