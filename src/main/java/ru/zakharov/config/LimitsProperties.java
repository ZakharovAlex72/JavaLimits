package ru.zakharov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "limits")
public class LimitsProperties {
    private BigDecimal initialLimit;

    public BigDecimal getInitialLimit() {
        return initialLimit;
    }

    public void setInitialLimit(BigDecimal initialLimit) {
        this.initialLimit = initialLimit;
    }

}
