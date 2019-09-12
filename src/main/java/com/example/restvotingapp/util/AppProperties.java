package com.example.restvotingapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    public String getTokenSecret() {
        return env.getProperty("tokenSecret");
    }

    public long getExpirationTime() {
        String expirationTime = env.getProperty("expirationTime");
        return Long.valueOf(expirationTime);
    }
}
