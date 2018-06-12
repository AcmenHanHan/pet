package com.logon.pet.petlogoncore.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "redis-cache")
public class RedisCacheSettings {

    private boolean enableTransaction;

    private Map<String, String> redisDefaultCacheConfiguration;

    private List<Map<String, String>> redisCacheConfigurationsWithName;

    public boolean isEnableTransaction() {
        return enableTransaction;
    }

    public void setEnableTransaction(boolean enableTransaction) {
        this.enableTransaction = enableTransaction;
    }

    public Map<String, String> getRedisDefaultCacheConfiguration() {
        return redisDefaultCacheConfiguration;
    }

    public void setRedisDefaultCacheConfiguration(Map<String, String> redisDefaultCacheConfiguration) {
        this.redisDefaultCacheConfiguration = redisDefaultCacheConfiguration;
    }

    public List<Map<String, String>> getRedisCacheConfigurationsWithName() {
        return redisCacheConfigurationsWithName;
    }

    public void setRedisCacheConfigurationsWithName(List<Map<String, String>> redisCacheConfigurationsWithName) {
        this.redisCacheConfigurationsWithName = redisCacheConfigurationsWithName;
    }
}
