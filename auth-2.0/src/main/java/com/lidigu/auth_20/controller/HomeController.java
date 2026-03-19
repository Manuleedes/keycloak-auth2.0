package com.lidigu.auth_20.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OidcUser principal, Model model){
        model.addAttribute( "username", principal.getPreferredUsername());
        model.addAttribute( "email", principal.getEmail());
        model.addAttribute("name", principal.getFullName());
        model.addAttribute("roles", principal.getAuthorities());
        return "home";
    }
}
