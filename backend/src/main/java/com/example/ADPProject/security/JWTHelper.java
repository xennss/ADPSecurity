package com.example.ADPProject.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTHelper {
    private static final String SECRET = "your_secret_key"; // Usa una clave secreta segura

    public static String createToken(String username) {
        return JWT.create()
                .withSubject(username)
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }
}
