package org.brac.microfinance.events;


import jakarta.persistence.Entity;
import java.util.UUID;
import org.brac.commons.primatives.Event;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "loan_accepted_events")
public class LoanAcceptedEvent extends Event {
  private UUID loanId;
  private UUID voucherId;
  private UUID disbursementId;

  public UUID getLoanId() {
    return loanId;
  }

  public void setLoanId(UUID loanId) {
    this.loanId = loanId;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(UUID voucherId) {
    this.voucherId = voucherId;
  }

  public UUID getDisbursementId() {
    return disbursementId;
  }

  public void setDisbursementId(UUID disbursementId) {
    this.disbursementId = disbursementId;
  }
}

