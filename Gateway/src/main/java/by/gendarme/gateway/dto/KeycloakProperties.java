package by.gendarme.gateway.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gateway.security.keycloak")
public class KeycloakProperties {
    private String introspectionUrl;
    private String clientId;
    private String clientSecret;
    private boolean onlineValidationEnabled = true;
}