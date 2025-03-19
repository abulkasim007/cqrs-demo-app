package org.brac.microfinance.events;

import jakarta.persistence.MappedSuperclass;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.brac.commons.primatives.Entity;

@MappedSuperclass
public class DisbursementInfo extends Entity {
  private OffsetDateTime date;
  private double amount;
  private UUID voucherId;

  public double getAmount() {
    return amount;
  }
  public void setAmount(double amount) {
    this.amount = amount;
  }
  public OffsetDateTime getDate() {
    return date;
  }
  public void setDate(OffsetDateTime date) {
    this.date = date;
  }
  private int disbursementStatus = 0;
  public UUID getVoucherId() {
    return voucherId;
  }
  public void setVoucherId(UUID voucherId) {
    this.voucherId = voucherId;
  }
}
