package org.brac.accounts.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.brac.accounts.events.JournalInfo;

@Entity
@Table(name = "journal_entities")
public class JournalEntity extends JournalInfo {

  @ManyToOne(fetch = FetchType.LAZY)
  private VoucherAggregateRoot voucherAggregateRoot;

  public VoucherAggregateRoot getVoucherAggregateRoot() {
    return voucherAggregateRoot;
  }

  public void setVoucherAggregateRoot(VoucherAggregateRoot voucherAggregateRoot) {
    this.voucherAggregateRoot = voucherAggregateRoot;
  }
}
