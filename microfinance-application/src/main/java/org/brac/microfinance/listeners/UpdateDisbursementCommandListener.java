package org.brac.microfinance.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.commons.utils.JsonHelpers;
import org.brac.microfinance.commands.UpdateDisbursementCommand;
import org.brac.microfinance.services.LoanService;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;
import org.springframework.stereotype.Service;

@Service
public class UpdateDisbursementCommandListener {

  private final LoanService loanService;

  public UpdateDisbursementCommandListener(LoanService loanService) {
    this.loanService = loanService;
  }


  @PulsarListener(subscriptionName = "Microfinance.Write", topics = "Microfinance.Commands.UpdateDisbursementCommand",
      schemaType = SchemaType.BYTES, autoStartup = "true", ackMode = AckMode.RECORD, subscriptionType = SubscriptionType.Shared)
  public void listen(byte[] message) throws IOException {
    UpdateDisbursementCommand command = JsonHelpers.getMessage(message, UpdateDisbursementCommand.class);
    loanService.updateDisbursement(command);
  }
}
