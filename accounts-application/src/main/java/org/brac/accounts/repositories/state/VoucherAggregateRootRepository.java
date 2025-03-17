package org.brac.accounts.repositories.state;

import java.util.UUID;
import org.brac.accounts.entities.VoucherAggregateRoot;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VoucherAggregateRootRepository extends ReactiveCrudRepository<VoucherAggregateRoot, UUID> {
}
