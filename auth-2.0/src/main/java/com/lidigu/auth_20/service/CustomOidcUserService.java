package com.lidigu.auth_20.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private final JwtDecoder jwtDecoder;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser defaultUser =  super.loadUser(userRequest);

        String accessToken = userRequest.getAccessToken().getTokenValue();
        Set<GrantedAuthority> keycloakRoles = extractKeyCloakRoles(accessToken);
        keycloakRoles.addAll(defaultUser.getAuthorities());
        return new DefaultOidcUser(
                keycloakRoles,
                defaultUser.getIdToken(),
                defaultUser.getUserInfo()
        );
    }

    private Set<GrantedAuthority> extractKeyCloakRoles(String accessToken) {
        //decode token
    Jwt jwt = jwtDecoder.decode(accessToken);
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null){
            return Collections.emptySet();
        }
        Map<String, Object> clientResource = (Map<String, Object>) resourceAccess.get(clientId);
        if (clientResource == null){
            return Collections.emptySet();
        }
        List<String> roles = (List<String>) clientResource.get("roles");
        if (roles == null || roles.isEmpty()){
            return Collections.emptySet();
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()))
                .collect(Collectors.toSet());

    }
}
