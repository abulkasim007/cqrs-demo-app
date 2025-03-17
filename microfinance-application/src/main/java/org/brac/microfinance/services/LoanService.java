package org.brac.microfinance.services;

import org.brac.microfinance.commands.DisburseCommand;
import org.brac.microfinance.commands.UpdateDisbursementCommand;
import org.brac.microfinance.entities.LoanAggregateRoot;
import org.brac.microfinance.events.DisbursementStatus;
import org.brac.microfinance.events.LoanAcceptedEvent;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.brac.microfinance.repositories.event.LoanAcceptedEventRepository;
import org.brac.microfinance.repositories.event.LoanDisbursementRequestedEventRepository;
import org.brac.microfinance.repositories.state.LoanAggregateRootRepository;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {
  private final LoanAggregateRootRepository loanAggregateRootRepository;
  private final LoanAcceptedEventRepository loanAcceptedEventRepository;
  private final LoanDisbursementRequestedEventRepository loanDisbursementRequestedEventRepository;
  private final PulsarTemplate<LoanDisbursementRequestedEvent> loanDisbursementRequestedEventPulsarTemplate;

  public LoanService(LoanAggregateRootRepository loanAggregateRootRepository,
                     LoanAcceptedEventRepository loanAcceptedEventRepository,
                     LoanDisbursementRequestedEventRepository loanDisbursementRequestedEventRepository,
                     PulsarTemplate<LoanDisbursementRequestedEvent> loanDisbursementRequestedEventPulsarTemplate) {
    this.loanAggregateRootRepository = loanAggregateRootRepository;
    this.loanAcceptedEventRepository = loanAcceptedEventRepository;
    this.loanDisbursementRequestedEventRepository = loanDisbursementRequestedEventRepository;
    this.loanDisbursementRequestedEventPulsarTemplate = loanDisbursementRequestedEventPulsarTemplate;
  }

  public void disburse(DisburseCommand command) {
    LoanAggregateRoot loanAggregateRoot = new LoanAggregateRoot();
    LoanDisbursementRequestedEvent loanDisbursementRequestedEvent =
        loanAggregateRoot.disburse(command.getLoanId(), command.getAmount(), command.getMemberId(),
            command.getTenantId(),
            command.getVerticalId());
    this.loanAggregateRootRepository.save(loanAggregateRoot);

    this.loanDisbursementRequestedEventRepository.save(loanDisbursementRequestedEvent);

    this.loanDisbursementRequestedEventPulsarTemplate.send("Microfinance.Events.LoanDisbursementRequestedEvent",
        loanDisbursementRequestedEvent);
  }

  @Transactional
  public void updateDisbursement(UpdateDisbursementCommand command) {
    LoanAggregateRoot loanAggregateRoot = this.loanAggregateRootRepository.findById(command.getLoanId()).orElseThrow();

    LoanAcceptedEvent loanAcceptedEvent =  loanAggregateRoot.updateDisbursement(command.getDisbursementId(), command.getVoucherId(),
        DisbursementStatus.ACCEPTED);

    this.loanAggregateRootRepository.save(loanAggregateRoot);

    this.loanAcceptedEventRepository.save(loanAcceptedEvent);
  }
}
