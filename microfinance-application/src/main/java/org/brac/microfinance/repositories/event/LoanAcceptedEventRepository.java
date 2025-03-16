package org.brac.microfinance.repositories.event;

import java.util.UUID;
import org.brac.microfinance.events.LoanAcceptedEvent;
import org.springframework.data.repository.CrudRepository;

public interface LoanAcceptedEventRepository extends CrudRepository<LoanAcceptedEvent, UUID> {
}