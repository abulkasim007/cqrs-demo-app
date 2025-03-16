package org.brac.accounts.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.brac.accounts.events.JournalEvent;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.brac.commons.primatives.AggregateRoot;

@Entity
@Table(name = "voucher_aggregate_roots")
public class VoucherAggregateRoot extends AggregateRoot {

  private static final UUID LOAN_ACCOUNT_HEAD = UUID.randomUUID();

  private UUID memberId;
  private double amount;
  private UUID loanId;

  @OneToMany(
      mappedBy = "voucherAggregateRoot",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<JournalEntity> journalEntities;

  public void create(UUID loanId, double amount, UUID memberId, UUID disbursementId, UUID tenantId, UUID verticalId) {

    UUID voucherId = UUID.randomUUID();


    this.setId(voucherId);
    this.setLoanId(loanId);
    this.setAmount(amount);
    this.setMemberId(memberId);
    this.assignEntityDefaults(memberId, tenantId, verticalId);

    Date journalDate = new Date();

    JournalEntity debitJournal = new JournalEntity();

    debitJournal.setId(UUID.randomUUID());
    debitJournal.setCredit(0);
    debitJournal.setDebit(amount);
    debitJournal.setHead(memberId);
    debitJournal.setDate(journalDate);
    debitJournal.setVoucherAggregateRoot(this);
    debitJournal.assignEntityDefaults(memberId, tenantId, verticalId);

    JournalEntity creditJournal = new JournalEntity();

    creditJournal.setId(UUID.randomUUID());
    creditJournal.setDebit(0);
    creditJournal.setCredit(amount);
    creditJournal.setHead(LOAN_ACCOUNT_HEAD);
    creditJournal.setDate(journalDate);
    creditJournal.setVoucherAggregateRoot(this);
    creditJournal.assignEntityDefaults(memberId, tenantId, verticalId);

    this.journalEntities = new HashSet<>();

    this.journalEntities.add(debitJournal);
    this.journalEntities.add(creditJournal);


    VoucherCreatedEvent voucherCreatedEvent = new VoucherCreatedEvent();


    voucherCreatedEvent.setAmount(amount);
    voucherCreatedEvent.setLoanId(loanId);
    voucherCreatedEvent.setMemberId(memberId);
    voucherCreatedEvent.setVoucherId(voucherId);
    voucherCreatedEvent.setDisbursementId(disbursementId);
    voucherCreatedEvent.setJournalEvents(from(journalEntities, voucherCreatedEvent, tenantId, verticalId));

    voucherCreatedEvent.setId(UUID.randomUUID());
    voucherCreatedEvent.setAggregateRootId(this.getId());
    voucherCreatedEvent.setAggregateRootVersion(this.getVersion());
    voucherCreatedEvent.setTimeStamp(new Date());
    voucherCreatedEvent.setSource(VoucherCreatedEvent.class.getName());
    voucherCreatedEvent.setCorrelationId(UUID.randomUUID());


    this.addEvent(voucherCreatedEvent);
  }

  private Set<JournalEvent> from(Set<JournalEntity> journalEntities,
                                 VoucherCreatedEvent voucherCreatedEvent, UUID tenantId, UUID verticalId) {

    Set<JournalEvent> journalEvents = new HashSet<>();

    for (JournalEntity journalEntity : journalEntities) {
      JournalEvent journalEvent = new JournalEvent();

      journalEvent.setId(journalEntity.getId());
      journalEvent.setDate(journalEntity.getDate());
      journalEvent.setCredit(journalEntity.getCredit());
      journalEvent.setDebit(journalEntity.getDebit());
      journalEvent.setHead(journalEntity.getHead());
      journalEvent.setVoucherCreatedEvent(voucherCreatedEvent);
      journalEvent.assignEntityDefaults(memberId, tenantId, verticalId);
      journalEvents.add(journalEvent);
    }

    return journalEvents;
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


  public UUID getLoanId() {
    return loanId;
  }

  public void setLoanId(UUID loanId) {
    this.loanId = loanId;
  }

  public Set<JournalEntity> getJournals() {
    return journalEntities;
  }

  public void setJournals(Set<JournalEntity> journals) {
    this.journalEntities = journals;
  }
}
