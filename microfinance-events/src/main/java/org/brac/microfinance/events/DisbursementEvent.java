package org.brac.microfinance.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "disbursement_events")
public class DisbursementEvent extends DisbursementInfo {


  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private LoanDisbursementRequestedEvent loanDisbursementRequestedEvent;

  public LoanDisbursementRequestedEvent getLoanDisbursementRequestedEvent() {
    return loanDisbursementRequestedEvent;
  }

  public void setLoanDisbursementRequestedEvent(
      LoanDisbursementRequestedEvent loanDisbursementRequestedEvent) {
    this.loanDisbursementRequestedEvent = loanDisbursementRequestedEvent;
  }
}