package com.alihmzyv.notebookrestapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.alihmzyv.notebookrestapi.repo")
public class NotebookDBConfig {
    @Bean(name = "notebook-datasource")
    @Primary
    @ConfigurationProperties(prefix = "app.notebook-datasource")
    public DataSource notebookDataSource(){
        return DataSourceBuilder.create().build();
    }
}
