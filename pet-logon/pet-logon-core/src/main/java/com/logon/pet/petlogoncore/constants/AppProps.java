package com.logon.pet.petlogoncore.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProps {

    private boolean redisEnabled;

    public boolean isRedisEnabled() {
        return redisEnabled;
    }

    public void setRedisEnabled(boolean redisEnabled) {
        this.redisEnabled = redisEnabled;
    }
}