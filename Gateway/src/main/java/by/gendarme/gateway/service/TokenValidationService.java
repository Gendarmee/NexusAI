package by.gendarme.gateway.service;

import reactor.core.publisher.Mono;

public interface TokenValidationService {
    Mono<Boolean> validateToken(String token);
}