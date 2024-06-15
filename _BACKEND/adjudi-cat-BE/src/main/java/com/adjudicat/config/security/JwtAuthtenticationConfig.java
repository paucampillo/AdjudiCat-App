package com.adjudicat.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.security.Key;


@NoArgsConstructor
public class JwtAuthtenticationConfig {

    private Key getSigningKey(String key) {
        byte[] ret = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(ret);
    }
    public String getJWTToken(String username, Long idUser) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String key = Consts.ADJUDICAT_SECRET_KEY;
        String token = Jwts
                .builder()
                .setId(String.valueOf(idUser))
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 315360000))
                .signWith(signatureAlgorithm, getSigningKey(key)).compact();

        return "Bearer " + token;
    }


}
