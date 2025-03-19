package org.brac.commons.primatives;

import org.springframework.data.annotation.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
public abstract class Event implements Persistable<UUID> {
  @Id
  private UUID id;
  private UUID aggregateRootId;
  private int aggregateRootVersion;
  private OffsetDateTime timeStamp;
  private String source;
  private UUID correlationId;

  public void setAggregateRootId(UUID aggregateRootId) {
    this.aggregateRootId = aggregateRootId;
  }
  public void setAggregateRootVersion(int aggregateRootVersion) {
    this.aggregateRootVersion = aggregateRootVersion;
  }
  public void setTimeStamp(OffsetDateTime timeStamp) {
    this.timeStamp = timeStamp;
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
  public void setCorrelationId(UUID correlationId) {
    this.correlationId = correlationId;
  }
  public boolean isNew() {
    return true;
  }
}
