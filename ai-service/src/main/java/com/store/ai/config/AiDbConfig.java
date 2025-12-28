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

@Configuration
@EnableJpaRepositories(
        basePackages = "com.store.ai.repository",
        entityManagerFactoryRef = "aiEmf",
        transactionManagerRef = "aiTxManager"
)
public class AiDbConfig {

    @Bean
    @ConfigurationProperties("ai.datasource")
    public DataSource aiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean aiEmf(
            EntityManagerFactoryBuilder builder,
            @Qualifier("aiDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.store.ai.entity")
                .persistenceUnit("ai")
                .build();
    }

    @Bean
    public PlatformTransactionManager aiTxManager(
            @Qualifier("aiEmf") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
