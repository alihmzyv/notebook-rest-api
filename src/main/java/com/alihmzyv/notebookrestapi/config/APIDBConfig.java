package com.alihmzyv.notebookrestapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class APIDBConfig {
    @Bean(name = "api-datasource")
    @ConfigurationProperties(prefix = "app.api-datasource")
    public DataSource apiDataSource(){
        return DataSourceBuilder.create().build();
    }
}
