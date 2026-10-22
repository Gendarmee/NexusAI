package by.gendarme.gateway.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "gateway.security")
public class GatewayProperties {
    private List<String> publicEndpoints = new ArrayList<>();
}