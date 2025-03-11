package org.brac.accounts.events;

import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;
import org.brac.commons.primatives.Entity;

@MappedSuperclass
public class JournalInfo extends Entity {
  private Date date;
  private double debit;
  private double credit;
  private UUID head;
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
  public double getDebit() {
    return debit;
  }

  public void setDebit(double debit) {
    this.debit = debit;
  }

  public double getCredit() {
    return credit;
  }

  public void setCredit(double credit) {
    this.credit = credit;
  }

  public UUID getHead() {
    return head;
  }

  public void setHead(UUID head) {
    this.head = head;
  }
}