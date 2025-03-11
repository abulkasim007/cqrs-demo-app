package org.brac.microfinance.events;

import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;
import org.brac.commons.primatives.Entity;

@MappedSuperclass
public class DisbursementInfo extends Entity {
  private Date date;
  private double amount;
  private UUID voucherId;
  private DisbursementStatus disbursementStatus;

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public DisbursementStatus getDisbursementStatus() {
    return disbursementStatus;
  }

  public void setDisbursementStatus(
      DisbursementStatus disbursementStatus) {
    this.disbursementStatus = disbursementStatus;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(UUID voucherId) {
    this.voucherId = voucherId;
  }
}
