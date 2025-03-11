package org.brac.microfinance.repositories;

import java.util.UUID;
import org.brac.microfinance.entities.LoanAggregateRoot;
import org.springframework.data.repository.CrudRepository;

public interface LoanAggregateRootRepository extends CrudRepository<LoanAggregateRoot, UUID> {
}
