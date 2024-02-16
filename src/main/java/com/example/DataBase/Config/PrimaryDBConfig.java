package com.example.DataBase.Config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "shopsOnlieEntityManagerFactory", transactionManagerRef = "shopsOnlineTransactionManager",
        basePackages = {"com.example.DataBase.Primary.Repository"})
public class PrimaryDBConfig {

    @Primary
    @Bean(name = "shopsOnlineDataSourceProperties")
    @ConfigurationProperties("spring.datasource.shopsonline")
    public DataSourceProperties shopsOnlineDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "shopsDataSource")
    public DataSource vendorDataSource() {
        return shopsOnlineDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "shopsOnlieEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean shopsOnlieEntityManagerFactory(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return entityManagerFactoryBuilder.dataSource(vendorDataSource())
                .packages("com.example.DataBase.Primary.Model")
                .build();
    }

    @Primary
    @Bean(name = "shopsOnlineTransactionManager")
    public PlatformTransactionManager shopsOnlineTransactionManager(@Qualifier("shopsOnlieEntityManagerFactory") EntityManagerFactory shopsOnlieEntityManagerFactory) {
        return new JpaTransactionManager(shopsOnlieEntityManagerFactory);
    }
}