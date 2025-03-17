package org.brac.accounts.repositories.state;

import java.util.UUID;
import org.brac.accounts.entities.JournalEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface JournalEntityRepository extends ReactiveCrudRepository<JournalEntity, UUID> {
}
