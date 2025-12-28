package com.store.ai.config;

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
        basePackages = "com.store.ai.repository",
        entityManagerFactoryRef = "aiEntityManagerFactory",
        transactionManagerRef = "aiTransactionManager"
)
public class AiConfig {

    @Bean
    @ConfigurationProperties("db3.datasource")
    public DataSource aiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "aiEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean aiEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(aiDataSource())
                .packages("com.store.ai.model")
                .persistenceUnit("aiPU")
                .properties(Map.of(
                        "hibernate.hbm2ddl.auto", "update",
                        "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"
                ))
                .build();
    }

    @Bean(name = "aiTransactionManager")
    public PlatformTransactionManager aiTransactionManager(
            @Qualifier("aiEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
