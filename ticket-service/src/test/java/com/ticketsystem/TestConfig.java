package com.ticketsystem;

import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Value("${spring.jpa.database-platform}")
    private String databasePlatform;

    @Bean
    public H2Dialect h2Dialect() {
        return new H2Dialect();
    }

    @Bean
    public String databasePlatform() {
        return "org.hibernate.dialect.H2Dialect";
    }

}
    