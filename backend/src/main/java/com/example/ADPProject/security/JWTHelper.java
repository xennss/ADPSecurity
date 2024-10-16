package com.example.ADPProject.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.springframework.stereotype.Component;

import java.util.Date;

public class JWTHelper {
    public static String createToken(String scopes) {
		
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    long fiveHoursInMillis = 1000 * 60 * 60 * 5;
		    Date expireDate = new Date(System.currentTimeMillis() + fiveHoursInMillis);
		    String token = JWT.create()
		    	.withSubject("apiuser")
		        .withIssuer("me@me.com")
		        .withClaim("scopes", scopes)
		        .withExpiresAt(expireDate)
		        .sign(algorithm);
		    return token;
		} catch (JWTCreationException exception){
			return null;
		}	
	}

    private static final String SECRET = "your_secret_key"; // Change this to a secure key
    private static final long EXPIRATION_TIME = 10 * 60 * 1000; // 10 minutes

    public String generateToken(String email) {
        return JWT.create()
	              .withSubject(email)
                  .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                  .sign(Algorithm.HMAC256(SECRET));
     }

    public String validateTokenAndRetrieveSubject(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getSubject();
     }
}
