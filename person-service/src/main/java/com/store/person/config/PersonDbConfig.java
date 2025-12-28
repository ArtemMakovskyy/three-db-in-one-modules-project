package com.store.person.config;

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
        basePackages = "com.store.person.repository",
        entityManagerFactoryRef = "personEmf",
        transactionManagerRef = "personTxManager"
)
public class PersonDbConfig {

    @Bean
    @ConfigurationProperties("person.datasource")
    public DataSource personDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean personEmf(
            EntityManagerFactoryBuilder builder,
            @Qualifier("personDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.store.person.entity")
                .persistenceUnit("person")
                .build();
    }

    @Bean
    public PlatformTransactionManager personTxManager(
            @Qualifier("personEmf") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
