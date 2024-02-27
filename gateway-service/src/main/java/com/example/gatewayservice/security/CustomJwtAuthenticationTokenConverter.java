package com.example.gatewayservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.List;

public class CustomJwtAuthenticationTokenConverter implements Converter<Jwt, Mono<JwtAuthenticationToken>> {

    @Override
    public Mono<JwtAuthenticationToken> convert(Jwt source) {
        List<String> authorities = (List<String>) source.getClaims().get("roles");
        JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(source, authorities.stream().map(SimpleGrantedAuthority::new).toList());
        return Mono.just(authentication);
    }
}
