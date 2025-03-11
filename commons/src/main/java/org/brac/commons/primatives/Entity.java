package org.brac.commons.primatives;


import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class Entity {
  @Id
  private UUID id;
  private UUID createdBy;
  private Date createdDate;
  private String language;
  private Date lastUpdatedDate;
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

  public UUID getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UUID createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Date getLastUpdatedDate() {
    return lastUpdatedDate;
  }

  public void setLastUpdatedDate(Date lastUpdatedDate) {
    this.lastUpdatedDate = lastUpdatedDate;
  }

  public UUID getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(UUID lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
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

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public boolean isMarkedToDelete() {
    return isMarkedToDelete;
  }

  public void setMarkedToDelete(boolean markedToDelete) {
    isMarkedToDelete = markedToDelete;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public void assignEntityDefaults(UUID userId, UUID tenantId, UUID verticalId) {

    Date currentTime = new Date();


    this.createdDate = currentTime;
    this.createdBy = userId;
    this.language = "en-US";
    this.tenantId = tenantId;
    this.serviceId = "test-service";
    this.verticalId = verticalId;
    this.lastUpdatedDate = currentTime;
    this.lastUpdatedBy = userId;
  }
}

