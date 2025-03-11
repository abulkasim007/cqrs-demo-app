package org.brac.accounts.services;

import org.brac.accounts.commands.CreateVoucherCommand;
import org.brac.accounts.entities.VoucherAggregateRoot;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.brac.accounts.repositories.VoucherAggregateRootRepository;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
  private final VoucherAggregateRootRepository voucherAggregateRootRepository;
  private final PulsarTemplate<VoucherCreatedEvent> voucherCreatedEventPulsarTemplate;

  public VoucherService(VoucherAggregateRootRepository voucherAggregateRootRepository,
                        PulsarTemplate<VoucherCreatedEvent> voucherCreatedEventPulsarTemplate) {
    this.voucherAggregateRootRepository = voucherAggregateRootRepository;
    this.voucherCreatedEventPulsarTemplate = voucherCreatedEventPulsarTemplate;
  }

  public void createVoucher(CreateVoucherCommand command) {
    VoucherAggregateRoot voucherAggregateRoot = new VoucherAggregateRoot();
    voucherAggregateRoot.create(command.getLoanId(), command.getAmount(), command.getMemberId(),
        command.getDisbursementId(), command.getTenantId(), command.getVerticalId());
    this.voucherAggregateRootRepository.save(voucherAggregateRoot);
    VoucherCreatedEvent voucherCreatedEvent = (VoucherCreatedEvent) voucherAggregateRoot.getEvents().getFirst();
    this.voucherCreatedEventPulsarTemplate.send("Accounts.Events.VoucherCreatedEvent", voucherCreatedEvent);
  }
}
