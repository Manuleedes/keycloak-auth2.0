package com.lidigu.auth_20.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OidcUser principal,
                      // @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authClient,
                       Model model){
        model.addAttribute( "username", principal.getPreferredUsername());
        model.addAttribute( "email", principal.getEmail());
        model.addAttribute("name", principal.getFullName());
        model.addAttribute("roles", principal.getAuthorities());
        //System.out.println(authClient.getAccessToken().getTokenValue());

        return "home";
    }
    @GetMapping("/student")
    public String student(){
        return "student";
    }
    @GetMapping("/lecturer")
    public String lecturer(){
        return "lecturer";
    }
    @GetMapping("/dean")
    public String dean(){
        return "dean";
    }

}
