package org.brac.microfinance.repositories.state;

import java.util.UUID;
import org.brac.microfinance.entities.LoanAggregateRoot;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanAggregateRootRepository extends ReactiveCrudRepository<LoanAggregateRoot, UUID> {
}
