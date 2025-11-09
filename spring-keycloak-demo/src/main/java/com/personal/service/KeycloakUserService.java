package com.personal.service;

import com.personal.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class KeycloakUserService {

    @Value("${keycloak.server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    private final RestTemplate restTemplate = new RestTemplate();

    // Get admin token
    private String getAdminAccessToken() {
        String tokenUrl = keycloakUrl + "/realms/master/protocol/openid-connect/token";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("username", adminUsername);
        form.add("password", adminPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<Map> response =
                restTemplate.exchange(tokenUrl, HttpMethod.POST, new HttpEntity<>(form, headers), Map.class);

        return (String) response.getBody().get("access_token");
    }

    public void registerUser(RegisterRequest request) {
        String token = getAdminAccessToken();

        // 1. Create user
        String createUserUrl = keycloakUrl + "/admin/realms/" + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> user = Map.of(
                "username", request.getUsername(),
                "email", request.getEmail(),
                "firstName", request.getFirstName(),
                "lastName", request.getLastName(),
                "enabled", true
        );

        ResponseEntity<String> response = restTemplate.exchange(
                createUserUrl, HttpMethod.POST, new HttpEntity<>(user, headers), String.class
        );

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Failed to create user in Keycloak: " + response.getStatusCode());
        }

        // 2. Get new user ID
        String getUserUrl = keycloakUrl + "/admin/realms/" + realm + "/users?username=" + request.getUsername();
        ResponseEntity<List> userSearchResp =
                restTemplate.exchange(getUserUrl, HttpMethod.GET, new HttpEntity<>(headers), List.class);

        Map<String, Object> createdUser = (Map<String, Object>) userSearchResp.getBody().get(0);
        String userId = (String) createdUser.get("id");

        // 3. Set password
        String passwordUrl = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";

        Map<String, Object> passwordPayload = Map.of(
                "type", "password",
                "value", request.getPassword(),
                "temporary", false
        );

        restTemplate.exchange(passwordUrl, HttpMethod.PUT,
                new HttpEntity<>(passwordPayload, headers), Void.class);
    }
}
