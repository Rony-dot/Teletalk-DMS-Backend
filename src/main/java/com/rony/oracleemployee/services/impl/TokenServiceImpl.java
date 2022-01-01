package com.rony.oracleemployee.services.impl;


import com.rony.oracleemployee.exception.InvalidTokenException;
import com.rony.oracleemployee.model.User;
import com.rony.oracleemployee.services.AuthSigninKeyResolver;
import com.rony.oracleemployee.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private AuthSigninKeyResolver authSigninKeyResolver;

    @Override
    public void validateToken(String jwtToken) throws InvalidTokenException {
        try {
            Jwts.parserBuilder()
                    .setSigningKeyResolver(authSigninKeyResolver)
                    .build().parse(jwtToken);
        } catch (ExpiredJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("invalid token provided");
            throw new InvalidTokenException("Invalid token", e);
        }
    }

    @Override
    public void generateToken(User userModel) {
        //TODO: Replace token with JWT
        String jwtToken = Jwts.builder()
                .setSubject(userModel.getUsername())
                .setAudience(userModel.getRoles() != null ? userModel.getRoles().toString() : List.of ().toString ())
                .setIssuer(String.valueOf(userModel.getId()))
                .signWith(authSigninKeyResolver.getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
        // Todo ask how this set jwtToken to the user model??? pass by value??
        userModel.setJwtToken(jwtToken);
    }
}
