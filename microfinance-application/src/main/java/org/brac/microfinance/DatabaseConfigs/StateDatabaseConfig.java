package org.brac.microfinance.DatabaseConfigs;


import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "stateEntityManagerFactory",
    transactionManagerRef = "stateTransactionManager",
    basePackages = {"org.brac.microfinance.repositories.state"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class StateDatabaseConfig {

  @Primary
  @Bean(name = "stateDataSource")
  @ConfigurationProperties(prefix = "spring.state.datasource")
  public DataSource stateDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "stateEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean stateEntityManagerFactory(
      EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("stateDataSource") DataSource dataSource) {

    return entityManagerFactoryBuilder.dataSource(dataSource)
        .packages("org.brac.microfinance.entities")
        .persistenceUnit("State")
        .build();
  }

  @Primary
  @Bean(name = "stateTransactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("stateEntityManagerFactory")
                                                       EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}