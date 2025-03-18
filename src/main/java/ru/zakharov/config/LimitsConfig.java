package ru.zakharov.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LimitsProperties.class)
public class LimitsConfig {
    private final LimitsProperties limitsProperties;

    public LimitsConfig(LimitsProperties limitsProperties) {
        this.limitsProperties = limitsProperties;
    }
}
