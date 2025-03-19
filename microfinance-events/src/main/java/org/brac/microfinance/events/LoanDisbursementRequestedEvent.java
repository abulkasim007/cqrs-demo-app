package org.brac.microfinance.events;

import jakarta.persistence.Entity;
import java.util.UUID;
import org.brac.commons.primatives.Event;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "loan_disbursement_requested_events")
public class LoanDisbursementRequestedEvent extends Event {
  private UUID loanId;
  private UUID memberId;
  private double amount;
  private UUID disbursementId;

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
