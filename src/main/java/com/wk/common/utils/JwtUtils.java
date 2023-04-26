package com.wk.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.wk.common.CommonConstants;
import com.wk.common.dto.VisitUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: JwtUtils
 * @description:
 * @author: dm
 * @create: 2021-09-26 14:51
 */
@Component
public class JwtUtils {

    Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    public static String CLAIM_KEY_CLIENT = "client";
    private static Map<String, Object> header = new HashMap<>();

    static {
        header.put("typ", "JWT");
        header.put("alg", SignatureAlgorithm.HS256.getValue());
    }


    @Value("login.jwtKey")
    String jwtKey;

    /**
     * 解析token字符串获取clamis
     */
    public Claims parseJwt(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(TextCodec.BASE64.encode(jwtKey)).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            LOGGER.warn("解析token报错={}", token);
        }
        return null;
    }

    /**
     * 获取用户id
     * @param httpServletRequest
     * @return
     */
    public Integer getUserId(HttpServletRequest httpServletRequest) {

        String token = getToken(httpServletRequest);
        if(StringUtils.isEmpty(token)){
            return null;
        }

        Claims claims = parseJwt(token);
        if(null==claims){
            LOGGER.info("解析token {} , claims为空",token);
            return null;
        }
        String subject = claims.getSubject();
        VisitUser visitUser = JSONObject.parseObject(subject,VisitUser.class);
        return visitUser.getId();
    }

    /**
     * 获取JWT TOKEN
     * @param httpServletRequest
     * @return
     */
    private String getToken(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(CommonConstants.TOKEN_HEADER);
        if(StringUtils.isBlank(token)) {
            token = httpServletRequest.getParameter(CommonConstants.ACCESS_TOKEN);
        }
        if(StringUtils.isEmpty(token)){
            LOGGER.info("jwt token为空");
            return null;
        }
        return token;
    }

    /**
     * 获取用户信息
     * @param httpServletRequest
     * @return
     */
    public VisitUser getVisitUser(HttpServletRequest httpServletRequest) {

        String token = getToken(httpServletRequest);
        if(StringUtils.isEmpty(token)){
            return null;
        }

        Claims claims = parseJwt(token);
        if(null==claims){
            LOGGER.info("解析token {} , claims为空",token);
            return null;
        }
        String subject = claims.getSubject();
        VisitUser sysUser = JSONObject.parseObject(subject,VisitUser.class);
        return sysUser;
    }

    /**
     *
     * @param client
     * @param id
     * @param subject
     * @return
     */
    public String createToken(String client, String id, String subject, Integer expireTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CLIENT, client);
        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setId(id) // 用户id
                .setSubject(subject) // 用户信息
                .setExpiration(generateExpirationDate(expireTime))
                .setIssuedAt(new Date())// 登录时间
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(jwtKey))
                .compact();
    }
    public String getIdFromToken(String token) {
        Claims claims = parseJwt(token);
        if(claims != null) {
            return claims.getId();
        }
        return null;
    }

    private Date generateExpirationDate(Integer expired) {
        LocalDateTime currentTime = LocalDateTime.now();
        return Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant().plusSeconds(expired));
    }


}
