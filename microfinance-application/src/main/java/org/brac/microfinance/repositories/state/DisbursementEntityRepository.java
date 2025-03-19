package org.brac.microfinance.repositories.state;

import java.util.UUID;
import org.brac.microfinance.entities.DisbursementEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DisbursementEntityRepository extends ReactiveCrudRepository<DisbursementEntity, UUID> {
}
