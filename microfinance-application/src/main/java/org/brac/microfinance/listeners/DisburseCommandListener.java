package org.brac.microfinance.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.commons.utils.JsonHelpers;
import org.brac.microfinance.commands.DisburseCommand;
import org.brac.microfinance.services.LoanService;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;
import org.springframework.stereotype.Service;

@Service
public class DisburseCommandListener {
  private final LoanService loanService;
  public DisburseCommandListener(LoanService loanService) {
    this.loanService = loanService;
  }

  @PulsarListener(subscriptionName = "Microfinance.Write", topics = "Microfinance.Commands.DisburseCommand",
      schemaType = SchemaType.BYTES, autoStartup = "true", ackMode = AckMode.RECORD, subscriptionType = SubscriptionType.Shared)
  public void listen(byte[] message) throws IOException {
    DisburseCommand command = JsonHelpers.getMessage(message, DisburseCommand.class);
    loanService.disburse(command);
  }
}