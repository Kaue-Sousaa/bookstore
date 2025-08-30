package com.bookstore.system.userservice.services;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bookstore.system.userservice.dtos.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import com.auth0.jwt.JWT;


@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final UserDetailsService userDetailsService;

    public TokenDto createAccessToken(String email, List<String> roles){
        LocalDateTime now = LocalDateTime.now();
        long validityInMilliseconds = 3600000;
        LocalDateTime validity = now.plusNanos(validityInMilliseconds * 1_000_000);
        return new TokenDto(
                getAccessToken(email, roles, now, validity));
    }


    public boolean validateToken(String token) {
        try {
            return !decodedToken(token).getExpiresAt().before(new Date());
        } catch (TokenExpiredException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        try {
            DecodedJWT decodedJWT = decodedToken(token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (Exception e) {
            throw new BadCredentialsException("Usuário inexistente ou senha inválida!");
        }
    }

    private DecodedJWT decodedToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret.getBytes())).build();
        return verifier.verify(token);
    }

    private String getAccessToken(String email, List<String> roles, LocalDateTime now, LocalDateTime validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(convertLocalDateTimeToDate(now))
                .withExpiresAt(convertLocalDateTimeToDate(validity))
                .withSubject(email)
                .withIssuer(issuerUrl)
                .sign(Algorithm.HMAC256(secret))
                .strip();
    }

    private static Date convertLocalDateTimeToDate(LocalDateTime now) {
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }
}
