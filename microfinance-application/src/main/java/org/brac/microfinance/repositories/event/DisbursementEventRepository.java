package org.brac.microfinance.repositories.event;

import java.util.UUID;
import org.brac.microfinance.events.DisbursementEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DisbursementEventRepository extends ReactiveCrudRepository<DisbursementEvent, UUID> {
}