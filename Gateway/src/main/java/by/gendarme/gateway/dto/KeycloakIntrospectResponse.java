package by.gendarme.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KeycloakIntrospectResponse(
        boolean active,
        String sub,
        Long exp,
        String username,
        String email
) {}