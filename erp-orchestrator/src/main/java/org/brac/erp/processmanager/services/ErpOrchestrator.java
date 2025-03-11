package org.brac.erp.processmanager.services;

import java.util.UUID;
import org.brac.accounts.commands.CreateVoucherCommand;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.brac.microfinance.commands.UpdateDisbursementCommand;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
public class ErpOrchestrator {

  private final PulsarTemplate<CreateVoucherCommand> createVoucherCommandPulsarTemplate;
  private final PulsarTemplate<UpdateDisbursementCommand> updateDisbursementCommandPulsarTemplate;


  public ErpOrchestrator(PulsarTemplate<CreateVoucherCommand> createVoucherCommandPulsarTemplate,
                         PulsarTemplate<UpdateDisbursementCommand> updateDisbursementCommandPulsarTemplate) {

    this.createVoucherCommandPulsarTemplate = createVoucherCommandPulsarTemplate;
    this.updateDisbursementCommandPulsarTemplate = updateDisbursementCommandPulsarTemplate;
  }

  public void handleVoucherCreatedEvent(VoucherCreatedEvent event) {
    UpdateDisbursementCommand updateDisbursementCommand = new UpdateDisbursementCommand();

    updateDisbursementCommand.setLoanId(event.getLoanId());
    updateDisbursementCommand.setVoucherId(event.getVoucherId());
    updateDisbursementCommand.setDisbursementId(event.getDisbursementId());

    this.updateDisbursementCommandPulsarTemplate.send("Microfinance.Commands.UpdateDisbursementCommand",
        updateDisbursementCommand);
  }

  public void handleLoanDisbursementRequestedEvent(LoanDisbursementRequestedEvent event) {

    CreateVoucherCommand createVoucherCommand = new CreateVoucherCommand();

    createVoucherCommand.setLoanId(event.getLoanId());
    createVoucherCommand.setAmount(event.getAmount());
    createVoucherCommand.setMemberId(event.getMemberId());
    createVoucherCommand.setDisbursementId(event.getDisbursementId());
    createVoucherCommand.setTenantId(UUID.randomUUID());
    createVoucherCommand.setVerticalId(UUID.randomUUID());

    this.createVoucherCommandPulsarTemplate.send("Accounts.Commands.CreateVoucherCommand", createVoucherCommand);
  }
}
