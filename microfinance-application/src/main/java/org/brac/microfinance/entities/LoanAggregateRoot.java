package org.brac.microfinance.entities;

import jakarta.persistence.Entity;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.brac.commons.primatives.AggregateRoot;
import org.brac.microfinance.events.DisbursementEvent;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "loan_aggregate_roots")
public class LoanAggregateRoot extends AggregateRoot {

  private static final UUID EMPTY_VOUCHER_ID = new UUID(0L, 0L);

  private UUID memberId;
  private double amount;

  public LoanDisbursementRequestedEvent disburse(UUID loanId, double amount, UUID memberId, UUID tenantId,
                                                 UUID verticalId, List<DisbursementEntity> disbursementEntities,
                                                 List<DisbursementEvent> disbursementEvents) {

    this.setId(loanId);
    this.setAmount(amount);
    this.setMemberId(memberId);

    this.assignEntityDefaults(memberId, tenantId, verticalId);


    for (int i = 0; i < 5; i++) {
      DisbursementEntity disbursementEntity = new DisbursementEntity();

      UUID disbursementId = UUID.randomUUID();

      disbursementEntity.setId(disbursementId);
      disbursementEntity.setDate(OffsetDateTime.now());
      disbursementEntity.setAmount(amount);
      disbursementEntity.setVoucherId(UUID.randomUUID());
      disbursementEntity.assignEntityDefaults(memberId, tenantId, verticalId);

      disbursementEntity.setVoucherId(EMPTY_VOUCHER_ID);
      disbursementEntities.add(disbursementEntity);
    }


    LoanDisbursementRequestedEvent loanDisbursementRequestedEvent = new LoanDisbursementRequestedEvent();

    loanDisbursementRequestedEvent.setLoanId(loanId);
    loanDisbursementRequestedEvent.setAmount(amount);
    loanDisbursementRequestedEvent.setMemberId(memberId);
    loanDisbursementRequestedEvent.setDisbursementId(
        disbursementEntities.stream().findAny().orElseThrow().getId());

    from(disbursementEntities, disbursementEvents, memberId, tenantId, verticalId);

    loanDisbursementRequestedEvent.setId(UUID.randomUUID());
    loanDisbursementRequestedEvent.setAggregateRootId(this.getId());
    loanDisbursementRequestedEvent.setAggregateRootVersion(this.getVersion());
    loanDisbursementRequestedEvent.setTimeStamp(OffsetDateTime.now());
    loanDisbursementRequestedEvent.setSource(LoanAggregateRoot.class.getName());
    loanDisbursementRequestedEvent.setCorrelationId(UUID.randomUUID());

    return loanDisbursementRequestedEvent;
  }

  private static void from(List<DisbursementEntity> disbursements,
                           List<DisbursementEvent> disbursementEvents,
                           UUID memberId, UUID tenantId, UUID verticalId) {

    for (DisbursementEntity disbursementEntity : disbursements) {
      DisbursementEvent disbursementEvent = new DisbursementEvent();
      disbursementEvent.setId(disbursementEntity.getId());
      disbursementEvent.setDate(disbursementEntity.getDate());
      disbursementEvent.setAmount(disbursementEntity.getAmount());
      disbursementEvent.assignEntityDefaults(memberId, tenantId, verticalId);
      disbursementEvent.setVoucherId(EMPTY_VOUCHER_ID);
      disbursementEvents.add(disbursementEvent);
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
