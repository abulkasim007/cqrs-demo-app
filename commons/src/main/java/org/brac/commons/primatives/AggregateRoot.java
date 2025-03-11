package org.brac.commons.primatives;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@MappedSuperclass
public abstract class AggregateRoot extends Entity {

  private final transient List<Event> events = new ArrayList<>();

  protected <T extends Event> void addEvent(T event) {
    events.add(event);
  }

  @Transient
  public List<Event> getEvents() {
    return Collections.unmodifiableList(events);
  }
}
