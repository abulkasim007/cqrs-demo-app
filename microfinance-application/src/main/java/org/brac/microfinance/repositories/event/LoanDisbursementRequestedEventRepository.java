package org.brac.microfinance.repositories.event;

import java.util.UUID;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanDisbursementRequestedEventRepository extends
    ReactiveCrudRepository<LoanDisbursementRequestedEvent, UUID> {
}