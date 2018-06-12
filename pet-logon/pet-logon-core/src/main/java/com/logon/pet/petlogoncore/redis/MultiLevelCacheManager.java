package com.logon.pet.petlogoncore.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

public class MultiLevelCacheManager implements CacheManager {


    @Override
    public Cache getCache(String s) {
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }
}
