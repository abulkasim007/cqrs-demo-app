package org.brac.microfinance.repositories.event;

import java.util.UUID;
import org.brac.microfinance.events.LoanAcceptedEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanAcceptedEventRepository extends ReactiveCrudRepository<LoanAcceptedEvent, UUID> {
}