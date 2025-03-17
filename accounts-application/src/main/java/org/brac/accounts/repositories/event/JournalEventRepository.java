package org.brac.accounts.repositories.event;

import java.util.UUID;
import org.brac.accounts.events.JournalEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface JournalEventRepository extends ReactiveCrudRepository<JournalEvent, UUID> {
}
