package org.brac.microfinance.commands;

import java.util.UUID;

public class DisburseCommand {

  private final double amount = 1;
  private final UUID loanId = UUID.randomUUID();
  private final UUID memberId = UUID.randomUUID();
  private final UUID tenantId = UUID.randomUUID();
  private final UUID verticalId = UUID.randomUUID();

  public double getAmount() {
    return amount;
  }

  public UUID getLoanId() {
    return loanId;
  }

  public UUID getMemberId() {
    return memberId;
  }

  public UUID getTenantId() {
    return tenantId;
  }

  public UUID getVerticalId() {
    return verticalId;
  }
}
