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

/*  private DisbursementStatus disbursementStatus;

  public DisbursementStatus getDisbursementStatus() {
    return disbursementStatus;
  }

  public void setDisbursementStatus(
      DisbursementStatus disbursementStatus) {
    this.disbursementStatus = disbursementStatus;
  }*/

  public UUID getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(UUID voucherId) {
    this.voucherId = voucherId;
  }

  public int getDisbursementStatus() {
    return disbursementStatus;
  }

  public void setDisbursementStatus(int disbursementStatus) {
    this.disbursementStatus = disbursementStatus;
  }
}
