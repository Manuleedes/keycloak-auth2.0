package com.lidigu.auth_20.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@Configuration
public class AppConfig {
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuer;
    @Bean
    public JwtDecoder jwtDecoder(){
       return JwtDecoders.fromIssuerLocation(issuer);
    }
}
