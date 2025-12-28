package com.store.application.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.store.application.repository.sale",
        entityManagerFactoryRef = "salesEntityManagerFactory",
        transactionManagerRef = "salesTransactionManager"
)
public class SalesConfig {

    @Bean
    @ConfigurationProperties("db2.datasource")
    public DataSource salesDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "salesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean salesEntityManagerFactory(
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(salesDataSource())
                .packages("com.store.application.model.sale")
                .persistenceUnit("salesPU")
                .properties(Map.of(
                        "hibernate.hbm2ddl.auto", "update",
                        "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"
                ))
                .build();
    }

    @Bean(name = "salesTransactionManager")
    public PlatformTransactionManager salesTransactionManager(
            @Qualifier("salesEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
