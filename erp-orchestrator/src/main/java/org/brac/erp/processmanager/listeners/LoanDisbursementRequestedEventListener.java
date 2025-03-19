package org.brac.erp.processmanager.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.commons.utils.JsonHelpers;
import org.brac.erp.processmanager.services.ErpOrchestrator;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.springframework.pulsar.reactive.config.annotation.ReactivePulsarListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LoanDisbursementRequestedEventListener {
  private final ErpOrchestrator erpOrchestrator;

  public LoanDisbursementRequestedEventListener(ErpOrchestrator erpOrchestrator) {
    this.erpOrchestrator = erpOrchestrator;
  }

  @ReactivePulsarListener(subscriptionName = "Erp.Orchestrator", topics = "Microfinance.Events.LoanDisbursementRequestedEvent",
      schemaType = SchemaType.BYTES, autoStartup = "true", subscriptionType = SubscriptionType.Shared)
  public Mono<MessageId> listen(byte[] message) throws IOException {
    LoanDisbursementRequestedEvent event = JsonHelpers.getMessage(message, LoanDisbursementRequestedEvent.class);
    return this.erpOrchestrator.handleLoanDisbursementRequestedEvent(event);
  }
}
