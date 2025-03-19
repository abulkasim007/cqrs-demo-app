package org.brac.erp.processmanager.services;

import java.util.UUID;
import org.apache.pulsar.client.api.MessageId;
import org.brac.accounts.commands.CreateVoucherCommand;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.brac.microfinance.commands.UpdateDisbursementCommand;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.springframework.pulsar.reactive.core.ReactivePulsarTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ErpOrchestrator {

  private final ReactivePulsarTemplate<CreateVoucherCommand> createVoucherCommandPulsarTemplate;
  private final ReactivePulsarTemplate<UpdateDisbursementCommand> updateDisbursementCommandPulsarTemplate;

  public ErpOrchestrator(ReactivePulsarTemplate<CreateVoucherCommand> createVoucherCommandPulsarTemplate,
                         ReactivePulsarTemplate<UpdateDisbursementCommand> updateDisbursementCommandPulsarTemplate) {

    this.createVoucherCommandPulsarTemplate = createVoucherCommandPulsarTemplate;
    this.updateDisbursementCommandPulsarTemplate = updateDisbursementCommandPulsarTemplate;
  }

  public Mono<MessageId> handleVoucherCreatedEvent(VoucherCreatedEvent event) {
    UpdateDisbursementCommand updateDisbursementCommand = new UpdateDisbursementCommand();

    updateDisbursementCommand.setLoanId(event.getLoanId());
    updateDisbursementCommand.setVoucherId(event.getVoucherId());
    updateDisbursementCommand.setDisbursementId(event.getDisbursementId());

   return this.updateDisbursementCommandPulsarTemplate.send("Microfinance.Commands.UpdateDisbursementCommand",
        updateDisbursementCommand);
  }

  public Mono<MessageId> handleLoanDisbursementRequestedEvent(LoanDisbursementRequestedEvent event) {

    CreateVoucherCommand createVoucherCommand = new CreateVoucherCommand();

    createVoucherCommand.setLoanId(event.getLoanId());
    createVoucherCommand.setAmount(event.getAmount());
    createVoucherCommand.setMemberId(event.getMemberId());
    createVoucherCommand.setDisbursementId(event.getDisbursementId());
    createVoucherCommand.setTenantId(UUID.randomUUID());
    createVoucherCommand.setVerticalId(UUID.randomUUID());

    return this.createVoucherCommandPulsarTemplate.send("Accounts.Commands.CreateVoucherCommand", createVoucherCommand);
  }
}
