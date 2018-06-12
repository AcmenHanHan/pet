package com.logon.pet.petlogoncore.shiro;


import org.apache.shiro.dao.DataAccessException;
import org.crazycake.shiro.IRedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ShiroCacheManager implements IRedisManager {

    private RedisTemplate<Serializable, Serializable> redisTemplate;

    public ShiroCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public byte[] get(byte[] key) {
        System.out.println(">>>>>>>>>>>>>enter ShiroCacheManager-get()");
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection)
                    throws DataAccessException {
                if (connection.exists(key)) {
                    return connection.get(key);
                }
                return null;
            }
        });
    }

    @Override
    public byte[] set(byte[] key, byte[] value, int expire) {
        System.out.println(">>>>>>>>>>>>>>enter ShiroCacheManager-set()");
//        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MILLISECONDS);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.setEx(key, expire, value);
                return null;
            }
        });
        return null;
    }

    @Override
    public void del(byte[] key) {
        System.out.println(">>>>>>>>>>>>>>enter ShiroCacheManager-del()");
        redisTemplate.delete(key);
    }

    @Override
    public Long dbSize() {
        return 0L;
    }

    @Override
    public Set<byte[]> keys(byte[] pattern) {
        return null;
    }
}
