package com.jornada_milhas.auth;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jornada_milhas.users.Users;

@Service
public class TokenService {

    private final String secret = "C67A930132D963BCFDC63AB5C2FE28E02296A370CB246BAEAED6755B2BB3B4BB";

    public String generateToken(Users user) {
        try {
            return JWT.create()
                    .withIssuer("jornada_milhas")
                    .withSubject(user.getEmail())
                    .withExpiresAt(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(12).toInstant())
                    .sign(Algorithm.HMAC256(Objects.requireNonNull(secret)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(Objects.requireNonNull(secret)))
                    .withIssuer("jornada_milhas")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
