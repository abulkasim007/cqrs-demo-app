package org.brac.microfinance.entities;


import jakarta.persistence.Entity;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.brac.microfinance.events.DisbursementInfo;
import org.brac.microfinance.events.LoanAcceptedEvent;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "disbursement_entities")
public class DisbursementEntity extends DisbursementInfo {

  public LoanAcceptedEvent updateDisbursement(UUID voucherId) {

    this.setModified();
    this.setVoucherId(voucherId);

    LoanAcceptedEvent loanAcceptedEvent = new LoanAcceptedEvent();
    loanAcceptedEvent.setLoanId(this.getId());
    loanAcceptedEvent.setDisbursementId(this.getId());
    loanAcceptedEvent.setVoucherId(this.getVoucherId());

    loanAcceptedEvent.setId(UUID.randomUUID());
    loanAcceptedEvent.setAggregateRootId(this.getId());
    loanAcceptedEvent.setAggregateRootVersion(this.getVersion());
    loanAcceptedEvent.setTimeStamp(OffsetDateTime.now());
    loanAcceptedEvent.setSource(LoanAcceptedEvent.class.getName());
    loanAcceptedEvent.setCorrelationId(UUID.randomUUID());

    return loanAcceptedEvent;
  }

}