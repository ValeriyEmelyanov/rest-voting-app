package com.example.restvotingapp.security;

import com.example.restvotingapp.SpringApplicationContext;
import com.example.restvotingapp.util.AppProperties;

public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }

    public static long getExpirationTime() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getExpirationTime();
    }

}
