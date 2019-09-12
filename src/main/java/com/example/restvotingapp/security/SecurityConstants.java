package com.example.restvotingapp.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 86400000L; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";

    public static final String TOKEN_SECRET = "1dublw5v9d4s9h";

//    public static String getTokenSecret() {
//        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
//        return appProperties.getTokenSecret();
//    }
}
