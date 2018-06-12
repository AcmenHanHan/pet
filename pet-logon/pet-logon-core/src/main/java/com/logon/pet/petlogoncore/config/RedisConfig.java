package com.logon.pet.petlogoncore.config;


import com.logon.pet.petlogoncore.redis.RedisCacheSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/3/1 14:45.
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    private Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    /**
     * 注入 RedisConnectionFactory
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    RedisCacheSettings redisCacheProps;

    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> functionDomainRedisTemplate() {
        logger.debug(">>>enter init redisTemplate");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式
     *
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }

    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 实例化 ValueOperations 对象,可以使用 String 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 实例化 ListOperations 对象,可以使用 List 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 实例化 SetOperations 对象,可以使用 Set 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    /**
     * @Unused
     * set RedisCacheConfiguration
     * @param redisDefaultCacheConfigurationMap
     * @param redisCacheConfiguration
     */
    private void setRedisCacheConfiguration(Map<String, String> redisDefaultCacheConfigurationMap, RedisCacheConfiguration redisCacheConfiguration) {
        if(redisDefaultCacheConfigurationMap != null&&redisCacheConfiguration != null){
            boolean enableExpire = Boolean.valueOf(redisDefaultCacheConfigurationMap.getOrDefault("enableExpire", "false"));
            if(enableExpire) {
                int expireTimeSecond = Integer.valueOf(redisDefaultCacheConfigurationMap.getOrDefault("expireTimeSecond", "0"));
                redisCacheConfiguration.entryTtl(Duration.ofSeconds(expireTimeSecond));
            }
            boolean usePrefix = Boolean.valueOf(redisDefaultCacheConfigurationMap.getOrDefault("usePrefix", "false"));
            if(usePrefix) {
                String prefixStr = redisDefaultCacheConfigurationMap.getOrDefault("prefixStr", "");
                redisCacheConfiguration.prefixKeysWith(prefixStr);
            }
            boolean enableNullValues = Boolean.valueOf(redisDefaultCacheConfigurationMap.getOrDefault("enableNullValues", "false"));
            if(enableNullValues) {
                redisCacheConfiguration.disableCachingNullValues();
            }
        }
    }

    /**
     * @param redisDefaultCacheConfigurationMap
     * @param redisCacheConfigurations
     */
    private void setRedisCacheConfigurationMap(List<Map<String, String>> redisDefaultCacheConfigurationMap, Map<String, RedisCacheConfiguration> redisCacheConfigurations) {
        if(redisDefaultCacheConfigurationMap != null&&redisCacheConfigurations != null) {
            for(Map<String, String> redisCacheConfigurationWithNameMap:redisCacheProps.getRedisCacheConfigurationsWithName()) {
                RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
                String cacheName = redisCacheConfigurationWithNameMap.getOrDefault("name", "");
                if(!StringUtils.isEmpty(cacheName)) {
                    boolean enableExpire = Boolean.valueOf(redisCacheConfigurationWithNameMap.getOrDefault("enableExpire", "false"));
                    if(enableExpire) {
                        int expireTimeSecond = Integer.valueOf(redisCacheConfigurationWithNameMap.getOrDefault("expireTimeSecond", "0"));
                        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(expireTimeSecond));
                    }
                    boolean usePrefix = Boolean.valueOf(redisCacheConfigurationWithNameMap.getOrDefault("usePrefix", "false"));
                    if(usePrefix) {
                        String prefixStr = redisCacheConfigurationWithNameMap.getOrDefault("prefixStr", "");
                        redisCacheConfiguration = redisCacheConfiguration.prefixKeysWith(prefixStr);
                    }
                    boolean enableNullValues = Boolean.valueOf(redisCacheConfigurationWithNameMap.getOrDefault("enableNullValues", "false"));
                    if(enableNullValues) {
                        redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
                    }
                    redisCacheConfigurations.put(cacheName, redisCacheConfiguration);
                }
            }
        }
    }

    @Bean(name = "redisCacheManager")
    @Override
    public RedisCacheManager cacheManager() {
        logger.debug(">>>enter init cacheManager");
        Map<String, RedisCacheConfiguration> cacheConfigurations = new ConcurrentHashMap<>();
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //setRedisCacheConfiguration(redisCacheProps.getRedisDefaultCacheConfiguration(), redisCacheConfiguration);

        Map<String, String> redisDefaultCacheConfigurationMap = redisCacheProps.getRedisDefaultCacheConfiguration();
        if(redisDefaultCacheConfigurationMap != null&&redisCacheConfiguration != null){
            boolean enableExpire = Boolean.valueOf(redisDefaultCacheConfigurationMap.getOrDefault("enableExpire", "false"));
            if(enableExpire) {
                int expireTimeSecond = Integer.valueOf(redisDefaultCacheConfigurationMap.getOrDefault("expireTimeSecond", "0"));
                redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(expireTimeSecond));
            }
            boolean usePrefix = Boolean.valueOf(redisDefaultCacheConfigurationMap.getOrDefault("usePrefix", "false"));
            if(usePrefix) {
                String prefixStr = redisDefaultCacheConfigurationMap.getOrDefault("prefixStr", "");
                redisCacheConfiguration = redisCacheConfiguration.prefixKeysWith(prefixStr);
            }
            boolean enableNullValues = Boolean.valueOf(redisDefaultCacheConfigurationMap.getOrDefault("enableNullValues", "false"));
            if(enableNullValues) {
                redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
            }
        }

        setRedisCacheConfigurationMap(redisCacheProps.getRedisCacheConfigurationsWithName(), cacheConfigurations);

        RedisCacheManager cacheManager = RedisCacheManager
                                            .RedisCacheManagerBuilder
                                            .fromConnectionFactory(redisConnectionFactory)
                                            .cacheDefaults(redisCacheConfiguration)
                                            .withInitialCacheConfigurations(cacheConfigurations)
                                            .build();
        cacheManager.setTransactionAware(redisCacheProps.isEnableTransaction());
        return cacheManager;
    }
}