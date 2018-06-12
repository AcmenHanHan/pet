package com.logon.pet.petlogoncore.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

public class MultiLevelCacheConfig {

    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        CacheManager ca2 = new CaffeineCacheManager();
        return ca2;
    }
}
