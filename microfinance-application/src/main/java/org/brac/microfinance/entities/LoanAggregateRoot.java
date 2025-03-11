package org.brac.microfinance.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.brac.commons.primatives.AggregateRoot;
import org.brac.microfinance.events.DisbursementEvent;
import org.brac.microfinance.events.DisbursementStatus;
import org.brac.microfinance.events.LoanAcceptedEvent;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "loan_aggregate_roots")
public class LoanAggregateRoot extends AggregateRoot {
  private UUID memberId;
  private double amount;

  public Set<DisbursementEntity> getDisbursementEntities() {
    return disbursementEntities;
  }

  public void setDisbursementEntities(
      Set<DisbursementEntity> disbursementEntities) {
    this.disbursementEntities = disbursementEntities;
  }

  @OneToMany(
      mappedBy = "loanAggregateRoot",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<DisbursementEntity> disbursementEntities;

  public void disburse(UUID loanId, double amount, UUID memberId, UUID tenantId, UUID verticalId) {

    this.setId(loanId);
    this.setAmount(amount);
    this.setMemberId(memberId);

    this.assignEntityDefaults(memberId, tenantId, verticalId);

    this.disbursementEntities = new HashSet<>();

    for (int i = 0; i < 5; i++) {
      DisbursementEntity disbursementEntity = new DisbursementEntity();

      UUID disbursementId = UUID.randomUUID();

      disbursementEntity.setId(disbursementId);
      disbursementEntity.setDate(new Date());
      disbursementEntity.setAmount(amount);
      disbursementEntity.setVoucherId(UUID.randomUUID());
      disbursementEntity.assignEntityDefaults(memberId, tenantId, verticalId);
      disbursementEntity.setLoanAggregateRoot(this);
      disbursementEntity.setDisbursementStatus(DisbursementStatus.PENDING);
      this.disbursementEntities.add(disbursementEntity);
    }


    LoanDisbursementRequestedEvent loanDisbursementRequestedEvent = new LoanDisbursementRequestedEvent();

    loanDisbursementRequestedEvent.setLoanId(loanId);
    loanDisbursementRequestedEvent.setAmount(amount);
    loanDisbursementRequestedEvent.setMemberId(memberId);
    loanDisbursementRequestedEvent.setDisbursementId(
        this.disbursementEntities.stream().findAny().orElseThrow().getId());
    loanDisbursementRequestedEvent.disbursementEvents =
        from(this.disbursementEntities, loanDisbursementRequestedEvent, memberId, tenantId, verticalId);

    this.addEvent(loanDisbursementRequestedEvent);
  }

  private static Set<DisbursementEvent> from(Set<DisbursementEntity> disbursements,
                                             LoanDisbursementRequestedEvent loanDisbursementRequestedEvent,
                                             UUID memberId, UUID tenantId, UUID verticalId) {

    Set<DisbursementEvent> disbursementEvents = new HashSet<>();

    for (DisbursementEntity disbursementEntity : disbursements) {
      DisbursementEvent disbursementEvent = new DisbursementEvent();

      disbursementEvent.setId(disbursementEntity.getId());
      disbursementEvent.setDate(disbursementEntity.getDate());
      disbursementEvent.setAmount(disbursementEntity.getAmount());
      disbursementEvent.assignEntityDefaults(memberId, tenantId, verticalId);
      disbursementEvent.setLoanDisbursementRequestedEvent(loanDisbursementRequestedEvent);
      disbursementEvent.setDisbursementStatus(disbursementEntity.getDisbursementStatus());

      disbursementEvents.add(disbursementEvent);
    }
    return disbursementEvents;
  }



  public void updateDisbursement(UUID disbursementId, UUID voucherId, DisbursementStatus disbursementStatus) {

    DisbursementEntity disbursementEntity =
        this.getDisbursementEntities().stream().filter(d -> d.getId().equals(disbursementId)).findFirst().orElseThrow();

    disbursementEntity.updateDisbursementStatus(voucherId, disbursementStatus);

    if (disbursementStatus == DisbursementStatus.ACCEPTED) {
      LoanAcceptedEvent loanAcceptedEvent = new LoanAcceptedEvent();
      loanAcceptedEvent.setLoanId(this.getId());
      loanAcceptedEvent.setDisbursementId(disbursementEntity.getId());
      loanAcceptedEvent.setVoucherId(disbursementEntity.getVoucherId());
      loanAcceptedEvent.setTopic(loanAcceptedEvent.getClass().getName());
      this.addEvent(loanAcceptedEvent);
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

}
