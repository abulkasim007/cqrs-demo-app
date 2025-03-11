package org.brac.microfinance.entities;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import org.brac.microfinance.events.DisbursementInfo;
import org.brac.microfinance.events.DisbursementStatus;

@jakarta.persistence.Entity
@Table(name = "disbursement_entities")
public class DisbursementEntity extends DisbursementInfo {

  @ManyToOne(fetch = FetchType.EAGER)
  private LoanAggregateRoot loanAggregateRoot;

  public LoanAggregateRoot getLoanAggregateRoot() {
    return loanAggregateRoot;
  }

  public void setLoanAggregateRoot(LoanAggregateRoot loanAggregateRoot) {
    this.loanAggregateRoot = loanAggregateRoot;
  }

  public void updateDisbursementStatus(UUID voucherId, DisbursementStatus disbursementStatus) {
    if (disbursementStatus == DisbursementStatus.ACCEPTED) {
      this.setVoucherId(voucherId);
    }
    this.setDisbursementStatus(disbursementStatus);
  }
}