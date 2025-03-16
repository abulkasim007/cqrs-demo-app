package org.brac.accounts.datasources;


import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "eventEntityManagerFactory",
    transactionManagerRef = "eventTransactionManager",
    basePackages = {"org.brac.accounts.repositories.event"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class EventDataSourceSetup {

  @Bean(name = "eventDataSource")
  @ConfigurationProperties(prefix = "spring.event.datasource")
  public DataSource eventDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "eventEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean eventEntityManagerFactory(
      EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("eventDataSource") DataSource dataSource) {

    return entityManagerFactoryBuilder.dataSource(dataSource)
        .packages("org.brac.accounts.events")
        .persistenceUnit("State")
        .build();
  }

  @Bean(name = "eventTransactionManager")
  public PlatformTransactionManager eventTransactionManager(@Qualifier("eventEntityManagerFactory")
                                                            EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}