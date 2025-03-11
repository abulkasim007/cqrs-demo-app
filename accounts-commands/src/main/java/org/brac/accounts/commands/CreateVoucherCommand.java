package org.brac.accounts.commands;

import java.util.UUID;

public class CreateVoucherCommand {
  private UUID loanId;
  private UUID memberId;
  private double amount;
  private UUID disbursementId;
  private UUID tenantId;
  private UUID verticalId;

  public UUID getLoanId() {
    return loanId;
  }

  public void setLoanId(UUID loanId) {
    this.loanId = loanId;
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

  public UUID getDisbursementId() {
    return disbursementId;
  }

  public void setDisbursementId(UUID disbursementId) {
    this.disbursementId = disbursementId;
  }

  public UUID getTenantId() {
    return tenantId;
  }

  public void setTenantId(UUID tenantId) {
    this.tenantId = tenantId;
  }

  public UUID getVerticalId() {
    return verticalId;
  }

  public void setVerticalId(UUID verticalId) {
    this.verticalId = verticalId;
  }
}

