package org.brac.erp.processmanager.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.brac.commons.utils.JsonHelpers;
import org.brac.erp.processmanager.services.ErpOrchestrator;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;
import org.springframework.stereotype.Service;

@Service
public class VoucherCreatedEventListener {
  private final ErpOrchestrator erpOrchestrator;

  public VoucherCreatedEventListener(ErpOrchestrator erpOrchestrator) {
    this.erpOrchestrator = erpOrchestrator;
  }

  @PulsarListener(subscriptionName = "Erp.Orchestrator", topics = "Accounts.Events.VoucherCreatedEvent",
      schemaType = SchemaType.BYTES, autoStartup = "true", ackMode = AckMode.RECORD, subscriptionType = SubscriptionType.Shared)
  public void listen(byte[] message) throws IOException {
    System.out.println(Thread.currentThread().isVirtual());
    VoucherCreatedEvent event = JsonHelpers.getMessage(message, VoucherCreatedEvent.class);
    this.erpOrchestrator.handleVoucherCreatedEvent(event);
  }
}
