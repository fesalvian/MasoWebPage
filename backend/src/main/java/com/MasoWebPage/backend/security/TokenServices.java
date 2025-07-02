package com.MasoWebPage.backend.security;

import com.MasoWebPage.backend.models.Usuario.Usuario;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServices {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UserDetails usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withIssuer("api-masoweb.com").withSubject(usuario.getUsername())
                .withClaim("role",usuario.getAuthorities().stream().toList())
                .withExpiresAt(dataExpiracao()).sign(algorithm);
    }

    public String getSubject(String tokenJWT) {

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("api-masoweb.com")
                    .build()
                    .verify(tokenJWT).getSubject();

        } catch (JWTVerificationException exception) {

            throw new RuntimeException(tokenJWT + " token invalido, ou expirado");
        }
    }


    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of(("-03:00")));
    }


    public String  gerarTokenComLogin(String login) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withIssuer("api-masoweb.com").withSubject(login).withExpiresAt(dataExpiracao()).sign(algorithm);
    }
}
