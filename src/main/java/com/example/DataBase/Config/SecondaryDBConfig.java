package com.example.DataBase.Config;

import com.example.DataBase.Primary.Model.Products;
import com.example.DataBase.Secondary.Model.Transactions;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "financialEntityManagerFactory", transactionManagerRef = "financialTransactionManager",
        basePackages = {"com.example.DataBase.Secondary.Repository"})
public class SecondaryDBConfig {
    @Bean(name = "financialDataSourceProperties")
    @ConfigurationProperties("spring.datasource.financial")
    public DataSourceProperties financialDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "financialDataSource")
    public DataSource vendorDataSource() {
        return financialDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "financialEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean financialEntityManagerFactory(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return entityManagerFactoryBuilder.dataSource(vendorDataSource())
                .packages("com.example.DataBase.Secondary.Model")
                .build();
    }

    @Bean(name = "financialTransactionManager")
    public PlatformTransactionManager financialTransactionManager(@Qualifier("financialEntityManagerFactory") EntityManagerFactory financialEntityManagerFactory) {
        return new JpaTransactionManager(financialEntityManagerFactory);
    }
}
