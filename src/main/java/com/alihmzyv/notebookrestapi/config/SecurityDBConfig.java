package com.alihmzyv.notebookrestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:security.datasource.properties")
public class SecurityDBConfig {
    private final Environment env;

    @Autowired
    public SecurityDBConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource securityDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(env.getProperty("driverClassName"))
                .url(env.getProperty("url"))
                .username(env.getProperty("user"))
                .password(env.getProperty("password"))
                .build();
    }
}
