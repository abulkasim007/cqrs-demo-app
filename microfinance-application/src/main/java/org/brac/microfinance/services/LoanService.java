package org.brac.microfinance.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.pulsar.client.api.MessageId;
import org.brac.microfinance.commands.DisburseCommand;
import org.brac.microfinance.commands.UpdateDisbursementCommand;
import org.brac.microfinance.entities.DisbursementEntity;
import org.brac.microfinance.entities.LoanAggregateRoot;
import org.brac.microfinance.events.DisbursementEvent;
import org.brac.microfinance.events.LoanAcceptedEvent;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.brac.microfinance.repositories.event.DisbursementEventRepository;
import org.brac.microfinance.repositories.event.LoanAcceptedEventRepository;
import org.brac.microfinance.repositories.event.LoanDisbursementRequestedEventRepository;
import org.brac.microfinance.repositories.state.DisbursementEntityRepository;
import org.brac.microfinance.repositories.state.LoanAggregateRootRepository;
import org.springframework.pulsar.reactive.core.ReactivePulsarTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LoanService {
  private final LoanAggregateRootRepository loanAggregateRootRepository;
  private final LoanAcceptedEventRepository loanAcceptedEventRepository;
  private final DisbursementEventRepository disbursementEventRepository;
  private final DisbursementEntityRepository disbursementEntityRepository;
  private final LoanDisbursementRequestedEventRepository loanDisbursementRequestedEventRepository;
  private final ReactivePulsarTemplate<LoanDisbursementRequestedEvent> loanDisbursementRequestedEventPulsarTemplate;

  public LoanService(LoanAggregateRootRepository loanAggregateRootRepository,
                     LoanAcceptedEventRepository loanAcceptedEventRepository,
                     DisbursementEventRepository disbursementEventRepository,
                     DisbursementEntityRepository disbursementEntityRepository,
                     LoanDisbursementRequestedEventRepository loanDisbursementRequestedEventRepository,
                     ReactivePulsarTemplate<LoanDisbursementRequestedEvent> loanDisbursementRequestedEventPulsarTemplate) {
    this.loanAggregateRootRepository = loanAggregateRootRepository;
    this.loanAcceptedEventRepository = loanAcceptedEventRepository;
    this.disbursementEventRepository = disbursementEventRepository;
    this.disbursementEntityRepository = disbursementEntityRepository;
    this.loanDisbursementRequestedEventRepository = loanDisbursementRequestedEventRepository;
    this.loanDisbursementRequestedEventPulsarTemplate = loanDisbursementRequestedEventPulsarTemplate;
  }

  public Mono<MessageId> disburse(DisburseCommand command) {
    LoanAggregateRoot loanAggregateRoot = new LoanAggregateRoot();

    List<DisbursementEvent> disbursementEvents = new ArrayList<>();
    List<DisbursementEntity> disbursementEntities = new ArrayList<>();

    LoanDisbursementRequestedEvent loanDisbursementRequestedEvent =
        loanAggregateRoot.disburse(command.getLoanId(), command.getAmount(), command.getMemberId(),
            command.getTenantId(),
            command.getVerticalId(), disbursementEntities, disbursementEvents);

    return this.loanAggregateRootRepository.save(loanAggregateRoot)
        .flatMap(savedVoucher -> this.disbursementEntityRepository.saveAll(disbursementEntities).collectList()
            .thenReturn(savedVoucher))
        .flatMap(event -> this.loanDisbursementRequestedEventRepository.save(loanDisbursementRequestedEvent)
            .thenReturn(loanDisbursementRequestedEvent))
        .flatMap(event -> this.disbursementEventRepository.saveAll(disbursementEvents).collectList()
            .thenReturn(loanDisbursementRequestedEvent))
        .flatMap(vce -> this.loanDisbursementRequestedEventPulsarTemplate
            .send("Microfinance.Events.LoanDisbursementRequestedEvent", loanDisbursementRequestedEvent)
        );
  }

  public Mono<Void> updateDisbursement(UpdateDisbursementCommand command) {
    return this.disbursementEntityRepository.findById(command.getDisbursementId())
        .flatMap(disbursementEntity -> {
          LoanAcceptedEvent loanAcceptedEvent =
              disbursementEntity.updateDisbursement(command.getVoucherId()/*, DisbursementStatus.ACCEPTED*/
              );
          return this.disbursementEntityRepository.save(disbursementEntity)
              .thenReturn(loanAcceptedEvent);

        })
        .flatMap(this.loanAcceptedEventRepository::save).then();
  }
}
