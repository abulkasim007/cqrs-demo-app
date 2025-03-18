package org.brac.accounts.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.accounts.commands.CreateVoucherCommand;
import org.brac.accounts.services.VoucherService;
import org.brac.commons.utils.JsonHelpers;
import org.springframework.pulsar.reactive.config.annotation.ReactivePulsarListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateVoucherCommandListener {
  private final VoucherService voucherService;

  public CreateVoucherCommandListener(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @ReactivePulsarListener(subscriptionName = "Accounts.Write", topics = "Accounts.Commands.CreateVoucherCommand",
      schemaType = SchemaType.BYTES, autoStartup = "true", subscriptionType = SubscriptionType.Shared)
  public Mono<MessageId> listen(byte[] message) throws IOException {
    CreateVoucherCommand command = JsonHelpers.getMessage(message, CreateVoucherCommand.class);
    return voucherService.createVoucher(command);
  }
}
