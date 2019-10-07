package com.example.restvotingapp.security;

import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtil {

    public static String getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token == null) {
            return null;
        }

        token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

        return Jwts.parser()
                .setSigningKey(SecurityConstants.getTokenSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
