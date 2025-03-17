package org.brac.erp.processmanager.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.brac.commons.utils.JsonHelpers;
import org.brac.erp.processmanager.services.ErpOrchestrator;
import org.springframework.pulsar.reactive.config.annotation.ReactivePulsarListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VoucherCreatedEventListener {
  private final ErpOrchestrator erpOrchestrator;

  public VoucherCreatedEventListener(ErpOrchestrator erpOrchestrator) {
    this.erpOrchestrator = erpOrchestrator;
  }

  @ReactivePulsarListener(subscriptionName = "Erp.Orchestrator", topics = "Accounts.Events.VoucherCreatedEvent",
      schemaType = SchemaType.BYTES, autoStartup = "true",  subscriptionType = SubscriptionType.Shared)
  public Mono<MessageId> listen(byte[] message) throws IOException {
    VoucherCreatedEvent event = JsonHelpers.getMessage(message, VoucherCreatedEvent.class);
   return this.erpOrchestrator.handleVoucherCreatedEvent(event);
  }
}
