package org.brac.commons.primatives;


import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
public abstract class Entity implements Persistable<UUID> {
  @Id
  private UUID id;
  private UUID createdBy;
  private OffsetDateTime createdDate;
  private String language;
  private OffsetDateTime lastUpdatedDate;
  private UUID lastUpdatedBy;
  private UUID tenantId;
  private UUID verticalId;
  private String serviceId;
  private boolean isMarkedToDelete;
  @Version
  private int version;


  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getVersion() {
    return version;
  }

  public void assignEntityDefaults(UUID userId, UUID tenantId, UUID verticalId) {

    OffsetDateTime currentTime = OffsetDateTime.now();

    this.createdDate = currentTime;
    this.createdBy = userId;
    this.language = "en-US";
    this.tenantId = tenantId;
    this.serviceId = "test-service";
    this.verticalId = verticalId;
    this.lastUpdatedDate = currentTime;
    this.lastUpdatedBy = userId;
  }

  @Transient
  private  boolean isModified;

  public void setModified() {
    this.isModified = true;
  }

  public boolean isNew() {
    return !this.isModified;
  }
}

