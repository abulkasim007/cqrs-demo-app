package org.brac.microfinance.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.commons.utils.JsonHelpers;
import org.brac.microfinance.commands.UpdateDisbursementCommand;
import org.brac.microfinance.services.LoanService;
import org.springframework.pulsar.reactive.config.annotation.ReactivePulsarListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateDisbursementCommandListener {

  private final LoanService loanService;

  public UpdateDisbursementCommandListener(LoanService loanService) {
    this.loanService = loanService;
  }


  @ReactivePulsarListener(subscriptionName = "Microfinance.Write", topics = "Microfinance.Commands.UpdateDisbursementCommand",
      schemaType = SchemaType.BYTES, autoStartup = "true", subscriptionType = SubscriptionType.Shared)
  public Mono<Void> listen(byte[] message) throws IOException {
    UpdateDisbursementCommand command = JsonHelpers.getMessage(message, UpdateDisbursementCommand.class);
    return loanService.updateDisbursement(command);
  }
}
