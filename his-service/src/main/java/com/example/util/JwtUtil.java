package com.example.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.dto.UserDto;

import java.time.Instant;

public class JwtUtil {

    private final long ttl;
    private final Algorithm algorithm;

    public JwtUtil(String secretKey, long ttl) {
        this.ttl = ttl;
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public String generateToken(UserDto user) {
        return JWT.create()
                .withClaim("org", user.getOrg())
                .withClaim("userId", user.getUserId())
                .withExpiresAt(Instant.now().plusSeconds(ttl))
                .sign(algorithm);
    }

    public UserDto validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT verify = verifier.verify(token);

        UserDto user = new UserDto();
        user.setOrg(verify.getClaim("org").asString());
        user.setUserId(verify.getClaim("userId").asString());
        return user;
    }
}
