package com.huangyuan.open.gray.config.provider.util;

import java.io.Serializable;

import java.util.Set;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.core.ValueOperations;

//Redis工具类
public class RedisUtil {


    private RedisTemplate<Serializable, Object> redisTemplate;

    // 批量删除对应的value
    public void remove(final String... keys) {

        for (String key : keys) {
            remove(key);
        }
    }


    // 批量删除key
    public void removePattern(final String pattern) {

        Set<Serializable> keys = redisTemplate.keys(pattern);

        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
}

    // 删除对应的value=
    public void remove(final String key) {

        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }


    // 删除符合要求的key by ivan
    public void deleteKeys(final String key) {

        redisTemplate.delete(redisTemplate.keys(key));

    }

    // 判断缓存中是否有对应的value
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    // 读取缓存
    public Object get(final String key) {
        Object result = null;

        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();

        result = operations.get(key);

        return result;
    }

    // 写入缓存
    public boolean set(final String key, Object value) {

        boolean result = false;

        try {

        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();

        operations.set(key, value);

        result = true;

        } catch (Exception e) {

        e.printStackTrace();

        }

        return result;

    }


    // 写入缓存
    public boolean set(final String key, Object value, Long expireTime) {

        boolean result = false;

        try {

        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();

        operations.set(key, value);

        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);

        result = true;

        } catch (Exception e) {

        e.printStackTrace();

        }

        return result;

    }


    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {

        this.redisTemplate = redisTemplate;

    }

}
