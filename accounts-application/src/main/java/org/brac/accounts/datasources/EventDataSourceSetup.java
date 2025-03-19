package org.brac.accounts.datasources;


import io.r2dbc.spi.ConnectionFactory;
import org.brac.accounts.repositories.event.JournalEventRepository;
import org.brac.accounts.repositories.event.VoucherCreatedEventRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@EnableR2dbcRepositories(entityOperationsRef = "eventEntityTemplate", basePackageClasses = {
    JournalEventRepository.class, VoucherCreatedEventRepository.class})
public class EventDataSourceSetup {

  @Bean
  @Qualifier(value = "eventConnectionFactory")
  public ConnectionFactory eventConnectionFactory() {
    return ConnectionFactoryBuilder.withUrl("r2dbc:postgresql://localhost/accounts_event_database")
        .username("postgres")
        .password("12345")
        .build();
  }

  @Bean
  public R2dbcEntityOperations eventEntityTemplate(
      @Qualifier("eventConnectionFactory") ConnectionFactory connectionFactory) {

    DefaultReactiveDataAccessStrategy strategy = new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE);
    DatabaseClient databaseClient = DatabaseClient.builder()
        .connectionFactory(connectionFactory)
        .bindMarkers(PostgresDialect.INSTANCE.getBindMarkersFactory())
        .build();

    return new R2dbcEntityTemplate(databaseClient, strategy);
  }
}