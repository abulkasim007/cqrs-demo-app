package org.brac.microfinance.repositories.event;

import java.util.UUID;
import org.brac.microfinance.events.LoanDisbursementRequestedEvent;
import org.springframework.data.repository.CrudRepository;

public interface LoanDisbursementRequestedEventRepository extends CrudRepository<LoanDisbursementRequestedEvent, UUID> {
}