package org.brac.microfinance.services;

import org.brac.microfinance.commands.DisburseCommand;
import org.brac.microfinance.commands.UpdateDisbursementCommand;
import org.brac.microfinance.entities.LoanAggregateRoot;
import org.brac.microfinance.events.DisbursementStatus;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.brac.microfinance.repositories.event.LoanDisbursementRequestedEventRepository;
import org.brac.microfinance.repositories.state.LoanAggregateRootRepository;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {
  private final LoanAggregateRootRepository loanAggregateRootRepository;
  private final LoanDisbursementRequestedEventRepository loanDisbursementRequestedEventRepository;
  private final PulsarTemplate<LoanDisbursementRequestedEvent> loanDisbursementRequestedEventPulsarTemplate;

  public LoanService(LoanAggregateRootRepository loanAggregateRootRepository,
                     LoanDisbursementRequestedEventRepository loanDisbursementRequestedEventRepository,
                     PulsarTemplate<LoanDisbursementRequestedEvent> loanDisbursementRequestedEventPulsarTemplate) {
    this.loanAggregateRootRepository = loanAggregateRootRepository;
    this.loanDisbursementRequestedEventRepository = loanDisbursementRequestedEventRepository;
    this.loanDisbursementRequestedEventPulsarTemplate = loanDisbursementRequestedEventPulsarTemplate;
  }

  public void disburse(DisburseCommand command) {
    LoanAggregateRoot loanAggregateRoot = new LoanAggregateRoot();
    loanAggregateRoot.disburse(command.getLoanId(), command.getAmount(), command.getMemberId(), command.getTenantId(),
        command.getVerticalId());
    this.loanAggregateRootRepository.save(loanAggregateRoot);

    LoanDisbursementRequestedEvent loanDisbursementRequestedEvent =
        (LoanDisbursementRequestedEvent) loanAggregateRoot.getEvents().getFirst();

    this.loanDisbursementRequestedEventRepository.save(loanDisbursementRequestedEvent);

    this.loanDisbursementRequestedEventPulsarTemplate.send("Microfinance.Events.LoanDisbursementRequestedEvent",
        loanDisbursementRequestedEvent);
  }

  @Transactional
  public void updateDisbursement(UpdateDisbursementCommand command) {
    LoanAggregateRoot loanAggregateRoot = this.loanAggregateRootRepository.findById(command.getLoanId()).orElseThrow();

    loanAggregateRoot.updateDisbursement(command.getDisbursementId(), command.getVoucherId(),
        DisbursementStatus.ACCEPTED);

    this.loanAggregateRootRepository.save(loanAggregateRoot);
  }
}
