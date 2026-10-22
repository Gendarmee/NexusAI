package by.gendarme.gateway.service.impl;

import by.gendarme.gateway.dto.KeycloakProperties;
import by.gendarme.gateway.service.TokenValidationService;
import by.gendarme.gateway.dto.KeycloakIntrospectResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KeycloakTokenValidationServiceImpl implements TokenValidationService {

    private final WebClient keycloakWebClient;
    private final KeycloakProperties keycloakProperties;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Boolean> validateToken(String token) {
        if (!keycloakProperties.isOnlineValidationEnabled()) {
            return Mono.just(true);
        }

        return keycloakWebClient.post()
                .uri(keycloakProperties.getIntrospectionUrl())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .headers(headers -> headers.setBasicAuth(
                        keycloakProperties.getClientId(),
                        keycloakProperties.getClientSecret()
                ))
                .body(BodyInserters.fromFormData("token", token))
                .retrieve()
                .bodyToMono(KeycloakIntrospectResponse.class)
                .map(KeycloakIntrospectResponse::active)
                .onErrorResume(e -> Mono.just(false));
    }
}
