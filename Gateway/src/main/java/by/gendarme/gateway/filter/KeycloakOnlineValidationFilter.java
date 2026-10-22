package by.gendarme.gateway.filter;

import by.gendarme.gateway.dto.GatewayProperties;
import by.gendarme.gateway.service.TokenValidationService;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class KeycloakOnlineValidationFilter implements WebFilter, Ordered {

    private final TokenValidationService tokenValidationService;
    private final GatewayProperties gatewayProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public KeycloakOnlineValidationFilter(TokenValidationService tokenValidationService,
                                          GatewayProperties gatewayProperties) {
        this.tokenValidationService = tokenValidationService;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        boolean isPublic = gatewayProperties.getPublicEndpoints().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        if (isPublic) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        return tokenValidationService.validateToken(token)
                .flatMap(isActive -> {
                    if (Boolean.TRUE.equals(isActive)) {
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}