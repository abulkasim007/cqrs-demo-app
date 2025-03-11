package org.brac.accounts.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "journal_events")
public class JournalEvent extends JournalInfo {


  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private VoucherCreatedEvent voucherCreatedEvent;

  public VoucherCreatedEvent getVoucherCreatedEvent() {
    return voucherCreatedEvent;
  }

  public void setVoucherCreatedEvent(VoucherCreatedEvent voucherCreatedEvent) {
    this.voucherCreatedEvent = voucherCreatedEvent;
  }
}
