package com.store.application.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.store.application.repository.person",
        entityManagerFactoryRef = "personEntityManagerFactory",
        transactionManagerRef = "personTransactionManager"
)
public class PersonConfig {

    @Primary
    @Bean
    @ConfigurationProperties("db1.datasource")
    public DataSource personDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "personEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean personEntityManagerFactory(
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(personDataSource())
                .packages("com.store.application.model.person")
                .persistenceUnit("personPU")
                .properties(Map.of(
                        "hibernate.hbm2ddl.auto", "update",
                        "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"
                ))
                .build();
    }

    @Primary
    @Bean(name = "personTransactionManager")
    public PlatformTransactionManager personTransactionManager(
            @Qualifier("personEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
