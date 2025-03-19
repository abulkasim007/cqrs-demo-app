package org.brac.microfinance.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.commons.utils.JsonHelpers;
import org.brac.microfinance.commands.DisburseCommand;
import org.brac.microfinance.services.LoanService;
import org.springframework.pulsar.reactive.config.annotation.ReactivePulsarListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DisburseCommandListener {
  private final LoanService loanService;
  public DisburseCommandListener(LoanService loanService) {
    this.loanService = loanService;
  }

  @ReactivePulsarListener(subscriptionName = "Microfinance.Write", topics = "Microfinance.Commands.DisburseCommand",
      schemaType = SchemaType.BYTES, autoStartup = "true",  subscriptionType = SubscriptionType.Shared)
  public Mono<MessageId> listen(byte[] message) throws IOException {
    DisburseCommand command = JsonHelpers.getMessage(message, DisburseCommand.class);
    return loanService.disburse(command);
  }
}