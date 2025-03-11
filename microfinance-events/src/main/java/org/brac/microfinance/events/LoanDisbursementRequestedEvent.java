package org.brac.microfinance.events;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import org.brac.commons.primatives.Event;

@jakarta.persistence.Entity
@Table(name = "loan_disbursement_requested_events")
public class LoanDisbursementRequestedEvent extends Event {
  private UUID loanId;
  private UUID memberId;
  private double amount;
  private UUID disbursementId;

  @OneToMany(
      mappedBy = "loanDisbursementRequestedEvent",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  public Set<DisbursementEvent> disbursementEvents;

  public UUID getLoanId() {
    return loanId;
  }

  public void setLoanId(UUID loanId) {
    this.loanId = loanId;
  }

  public UUID getMemberId() {
    return memberId;
  }

  public void setMemberId(UUID memberId) {
    this.memberId = memberId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public UUID getDisbursementId() {
    return disbursementId;
  }

  public void setDisbursementId(UUID disbursementId) {
    this.disbursementId = disbursementId;
  }
}
