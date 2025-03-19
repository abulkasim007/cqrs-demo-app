package org.brac.microfinance.commands;

import java.util.UUID;

public class UpdateDisbursementCommand {
  private UUID loanId;
  private UUID voucherId;
  private UUID disbursementId;
  private final UUID tenantId= UUID.randomUUID();
  private final UUID verticalId= UUID.randomUUID();

  public void setLoanId(UUID loanId) {
    this.loanId = loanId;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(UUID voucherId) {
    this.voucherId = voucherId;
  }

  public UUID getDisbursementId() {
    return disbursementId;
  }

  public void setDisbursementId(UUID disbursementId) {
    this.disbursementId = disbursementId;
  }
}

