package org.brac.microfinance.datasources;


import io.r2dbc.spi.ConnectionFactory;
import org.brac.microfinance.repositories.state.DisbursementEntityRepository;
import org.brac.microfinance.repositories.state.LoanAggregateRootRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@EnableR2dbcRepositories(entityOperationsRef = "stateEntityTemplate", basePackageClasses = {
    LoanAggregateRootRepository.class, DisbursementEntityRepository.class})
public class StateDataSourceSetup {

  @Primary
  @Bean
  @Qualifier(value = "stateConnectionFactory")
  public ConnectionFactory stateConnectionFactory() {
    return ConnectionFactoryBuilder.withUrl("r2dbc:postgresql://localhost/microfinance_state_database")
        .username("postgres")
        .password("12345")
        .build();
  }

  @Bean
  public R2dbcEntityOperations stateEntityTemplate(
      @Qualifier("stateConnectionFactory") ConnectionFactory connectionFactory) {

    DefaultReactiveDataAccessStrategy strategy = new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE);
    DatabaseClient databaseClient = DatabaseClient.builder()
        .connectionFactory(connectionFactory)
        .bindMarkers(PostgresDialect.INSTANCE.getBindMarkersFactory())
        .build();

    return new R2dbcEntityTemplate(databaseClient, strategy);
  }
}