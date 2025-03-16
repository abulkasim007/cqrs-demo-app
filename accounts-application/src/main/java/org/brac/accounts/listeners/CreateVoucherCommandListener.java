package org.brac.accounts.listeners;

import java.io.IOException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.brac.accounts.commands.CreateVoucherCommand;
import org.brac.accounts.services.VoucherService;
import org.brac.commons.utils.JsonHelpers;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;
import org.springframework.stereotype.Service;

@Service
public class CreateVoucherCommandListener {
  private final VoucherService voucherService;

  public CreateVoucherCommandListener(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @PulsarListener(subscriptionName = "Accounts.Write", topics = "Accounts.Commands.CreateVoucherCommand",
      schemaType = SchemaType.BYTES, autoStartup = "true", ackMode = AckMode.RECORD, subscriptionType = SubscriptionType.Shared)
  public void listen(byte[] message) throws IOException {
    System.out.println(Thread.currentThread().isVirtual());
    CreateVoucherCommand command = JsonHelpers.getMessage(message, CreateVoucherCommand.class);
    voucherService.createVoucher(command);
  }
}
