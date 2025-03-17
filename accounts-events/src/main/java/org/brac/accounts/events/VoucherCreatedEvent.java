package org.brac.accounts.events;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import org.brac.commons.primatives.Event;

@Entity
@Table(name = "voucher_created_event")
public class VoucherCreatedEvent extends Event {

  private double amount;
  private UUID loanId;
  private UUID voucherId;
  private UUID memberId;
  private UUID disbursementId;

  public void setAmount(double amount) {
    this.amount = amount;
  }

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

  public UUID getMemberId() {
    return memberId;
  }

  public void setMemberId(UUID memberId) {
    this.memberId = memberId;
  }

  public UUID getDisbursementId() {
    return disbursementId;
  }

  public void setDisbursementId(UUID disbursementId) {
    this.disbursementId = disbursementId;
  }
}
