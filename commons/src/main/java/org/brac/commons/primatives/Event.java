package org.brac.commons.primatives;


import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class Event {
  @Id
  private UUID id;
  private UUID aggregateRootId;
  private int aggregateRootVersion;
  private Date timeStamp;
  private String source;

  private UUID correlationId;

  public UUID getAggregateRootId() {
    return aggregateRootId;
  }

  public void setAggregateRootId(UUID aggregateRootId) {
    this.aggregateRootId = aggregateRootId;
  }

  public int getAggregateRootVersion() {
    return aggregateRootVersion;
  }

  public void setAggregateRootVersion(int aggregateRootVersion) {
    this.aggregateRootVersion = aggregateRootVersion;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }



  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(UUID correlationId) {
    this.correlationId = correlationId;
  }
}
