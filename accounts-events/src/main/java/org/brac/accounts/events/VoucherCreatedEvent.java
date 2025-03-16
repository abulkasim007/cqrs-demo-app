package org.brac.accounts.events;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
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

  @OneToMany(
      mappedBy = "voucherCreatedEvent",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<JournalEvent> journalEvents;



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

  public Set<JournalEvent> getJournalEvents() {
    return journalEvents;
  }

  public void setJournalEvents(Set<JournalEvent> journalEvents) {
    this.journalEvents = journalEvents;
  }
}
