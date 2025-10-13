package com.personal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class ControllerEanpoint {

    @GetMapping("/")
    public String publicEndpoint1() {
        return "Home page";
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "public";
    }

//    // For users logged in via Keycloak (OAuth2 client login)
//    @GetMapping("/user")
//    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User user) {
//        if (user == null) {
//            return Map.of("error", "No OAuth2User found — login required");
//        }
//        return Map.of(
//                "loginType", "OAuth2 Login",
//                "name", user.getAttribute("name"),
//                "email", user.getAttribute("email")
//        );
//    }

    // For API clients with JWT (Bearer token)
    @GetMapping("/api/secure")
    public Map<String, Object> secure(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return Map.of("error", "No JWT found — use Authorization: Bearer <token>");
        }
        return Map.of(
                "loginType", "JWT Access Token" + jwt.getTokenValue(),
                "subject", jwt.getSubject(),
                "email", jwt.getClaim("email"),
                "name", jwt.getClaim("preferred_username")
        );
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('role_admin')")
    public String admin() {
        return "admin area";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('role_user')")
    public String user() {

        return "user area";
    }
}
