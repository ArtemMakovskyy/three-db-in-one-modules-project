package com.store.sales.config;

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
        basePackages = "com.store.sales.repository",
        entityManagerFactoryRef = "salesEmf",
        transactionManagerRef = "salesTxManager"
)
public class SalesDbConfig {

    @Bean
    @ConfigurationProperties("sales.datasource")
    public DataSource salesDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean salesEmf(
            EntityManagerFactoryBuilder builder,
            @Qualifier("salesDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.store.sales.entity")
                .persistenceUnit("sales")
                .build();
    }

    @Bean
    public PlatformTransactionManager salesTxManager(
            @Qualifier("salesEmf") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
