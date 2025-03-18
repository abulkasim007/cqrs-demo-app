package org.brac.accounts.entities;


import jakarta.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.brac.accounts.events.JournalEvent;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.brac.commons.primatives.AggregateRoot;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "voucher_aggregate_roots")
public class VoucherAggregateRoot extends AggregateRoot {

  private static final UUID LOAN_ACCOUNT_HEAD = UUID.randomUUID();

  private UUID memberId;
  private double amount;
  private UUID loanId;


  public VoucherCreatedEvent create(UUID loanId, double amount, UUID memberId, UUID disbursementId, UUID tenantId,
                                    UUID verticalId,
                                    List<JournalEntity> journalEntities, List<JournalEvent> journalEvents) {

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
    debitJournal.assignEntityDefaults(memberId, tenantId, verticalId);

    JournalEntity creditJournal = new JournalEntity();

    creditJournal.setId(UUID.randomUUID());
    creditJournal.setDebit(0);
    creditJournal.setCredit(amount);
    creditJournal.setHead(LOAN_ACCOUNT_HEAD);
    creditJournal.setDate(journalDate);
    creditJournal.assignEntityDefaults(memberId, tenantId, verticalId);


    journalEntities.add(debitJournal);
    journalEntities.add(creditJournal);


    VoucherCreatedEvent voucherCreatedEvent = new VoucherCreatedEvent();


    from(journalEntities, journalEvents, tenantId, verticalId);

    voucherCreatedEvent.setAmount(amount);
    voucherCreatedEvent.setLoanId(loanId);
    voucherCreatedEvent.setMemberId(memberId);
    voucherCreatedEvent.setVoucherId(voucherId);
    voucherCreatedEvent.setDisbursementId(disbursementId);


    voucherCreatedEvent.setId(UUID.randomUUID());
    voucherCreatedEvent.setAggregateRootId(this.getId());
    voucherCreatedEvent.setAggregateRootVersion(this.getVersion());
    voucherCreatedEvent.setTimeStamp(new Date());
    voucherCreatedEvent.setSource(VoucherCreatedEvent.class.getName());
    voucherCreatedEvent.setCorrelationId(UUID.randomUUID());


    return voucherCreatedEvent;
  }

  private void from(List<JournalEntity> journalEntities, List<JournalEvent> journalEvents, UUID tenantId,
                    UUID verticalId) {


    for (JournalEntity journalEntity : journalEntities) {
      JournalEvent journalEvent = new JournalEvent();

      journalEvent.setId(journalEntity.getId());
      journalEvent.setDate(journalEntity.getDate());
      journalEvent.setCredit(journalEntity.getCredit());
      journalEvent.setDebit(journalEntity.getDebit());
      journalEvent.setHead(journalEntity.getHead());
      journalEvent.assignEntityDefaults(memberId, tenantId, verticalId);
      journalEvents.add(journalEvent);
    }

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
}
