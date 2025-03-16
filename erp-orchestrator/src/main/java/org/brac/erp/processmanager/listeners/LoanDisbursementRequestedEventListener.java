package org.brac.erp.processmanager.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.commons.utils.JsonHelpers;
import org.brac.erp.processmanager.services.ErpOrchestrator;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;
import org.springframework.stereotype.Service;

@Service
public class LoanDisbursementRequestedEventListener {

  private final ErpOrchestrator erpOrchestrator;

  public LoanDisbursementRequestedEventListener(ErpOrchestrator erpOrchestrator) {
    this.erpOrchestrator = erpOrchestrator;
  }


  @PulsarListener(subscriptionName = "Erp.Orchestrator", topics = "Microfinance.Events.LoanDisbursementRequestedEvent",
      schemaType = SchemaType.BYTES, autoStartup = "true", ackMode = AckMode.RECORD, subscriptionType = SubscriptionType.Shared)
  public void listen(byte[] message) throws IOException {
    LoanDisbursementRequestedEvent event = JsonHelpers.getMessage(message, LoanDisbursementRequestedEvent.class);
    this.erpOrchestrator.handleLoanDisbursementRequestedEvent(event);
  }
}
