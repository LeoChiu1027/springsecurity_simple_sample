package com.armi.sample.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.armi.sample.jpa.repository",
        entityManagerFactoryRef = "defaultEntityManagerFactory",
        transactionManagerRef = "defaultTransactionManager")
public class JpaConfig {

    @Value("${hibernate.show_sql:true}")
    private String showSql;

    @Primary
    @Bean
    LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(defaultDataSource());
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        em.setPersistenceUnitName("smart");
        em.setPackagesToScan("com.armi.sample.jpa.entity");
        return em;
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix="datasource.smart")
    DataSource defaultDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    PlatformTransactionManager defaultTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager(defaultEntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.proc.param_null_passing", "true");
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
//        properties.setProperty("org.hibernate.flushMode", FlushMode.COMMIT.toString());
        return properties;
    }

}