package com.wk.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: RedisCheckUtil
 * @description:
 * @author: dm
 * @create: 2021-7-19 11:12:13
 */
@Component
public class RedisCheckUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean check(String key ,String value , long time){
        if (redisTemplate.opsForValue().setIfAbsent(key , value , time , TimeUnit.SECONDS)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean unLock(String key){
        return redisTemplate.delete(key);
    }

}
