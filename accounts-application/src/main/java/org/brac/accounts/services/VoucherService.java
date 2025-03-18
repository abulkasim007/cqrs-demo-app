package org.brac.accounts.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.pulsar.client.api.MessageId;
import org.brac.accounts.commands.CreateVoucherCommand;
import org.brac.accounts.entities.JournalEntity;
import org.brac.accounts.entities.VoucherAggregateRoot;
import org.brac.accounts.events.JournalEvent;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.brac.accounts.repositories.event.JournalEventRepository;
import org.brac.accounts.repositories.event.VoucherCreatedEventRepository;
import org.brac.accounts.repositories.state.JournalEntityRepository;
import org.brac.accounts.repositories.state.VoucherAggregateRootRepository;
import org.springframework.pulsar.reactive.core.ReactivePulsarTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VoucherService {
  private final JournalEventRepository journalEventRepository;
  private final JournalEntityRepository journalEntityRepository;
  private final VoucherCreatedEventRepository voucherCreatedEventRepository;
  private final VoucherAggregateRootRepository voucherAggregateRootRepository;
  private final ReactivePulsarTemplate<VoucherCreatedEvent> voucherCreatedEventPulsarTemplate;

  public VoucherService(JournalEventRepository journalEventRepository,
                        JournalEntityRepository journalEntityRepository,
                        VoucherCreatedEventRepository voucherCreatedEventRepository,
                        VoucherAggregateRootRepository voucherAggregateRootRepository,
                        ReactivePulsarTemplate<VoucherCreatedEvent> voucherCreatedEventPulsarTemplate) {
    this.journalEventRepository = journalEventRepository;
    this.journalEntityRepository = journalEntityRepository;
    this.voucherCreatedEventRepository = voucherCreatedEventRepository;
    this.voucherAggregateRootRepository = voucherAggregateRootRepository;
    this.voucherCreatedEventPulsarTemplate = voucherCreatedEventPulsarTemplate;
  }

  public Mono<MessageId> createVoucher(CreateVoucherCommand command) {
    List<JournalEvent> journalEvents = new ArrayList<>();
    List<JournalEntity> journalEntities = new ArrayList<>();

    VoucherAggregateRoot voucherAggregateRoot = new VoucherAggregateRoot();

    VoucherCreatedEvent voucherCreatedEvent = voucherAggregateRoot.create(
        command.getLoanId(), command.getAmount(), command.getMemberId(),
        command.getDisbursementId(), command.getTenantId(), command.getVerticalId(), journalEntities, journalEvents);

   return this.voucherAggregateRootRepository.save(voucherAggregateRoot)
        .flatMap(savedVoucher -> this.journalEntityRepository.saveAll(journalEntities).collectList()
            .thenReturn(savedVoucher))
        .flatMap(event -> this.voucherCreatedEventRepository.save(voucherCreatedEvent)
            .thenReturn(voucherCreatedEvent))
        .flatMap(event -> this.journalEventRepository.saveAll(journalEvents).collectList()
            .thenReturn(voucherCreatedEvent))
        .flatMap(vce -> this.voucherCreatedEventPulsarTemplate
            .send("Accounts.Events.VoucherCreatedEvent", voucherCreatedEvent)
        );
  }
}
