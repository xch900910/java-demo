package com.example.day.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-07-21 19:49
 **/

public class TokenUtil {
    public static String getToken(String apiSecret) {
        String token = "";
        token = JWT.create()
                .sign(Algorithm.HMAC256(apiSecret));
        return token;
    }

    public static void main(String[] args) {
        System.out.println(JWT.create()
                .sign(Algorithm.HMAC256("project-public-platform")));
    }
}
