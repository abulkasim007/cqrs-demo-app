package org.brac.microfinance.events;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import org.brac.commons.primatives.Event;

@Entity
@Table(name = "loan_accepted_events")
public class LoanAcceptedEvent extends Event  {
  private UUID loanId;
  private UUID voucherId;
  private UUID disbursementId;
  private String topic;

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

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }
}

