package org.brac.accounts.repositories.state;

import java.util.UUID;
import org.brac.accounts.entities.VoucherAggregateRoot;
import org.springframework.data.repository.CrudRepository;

public interface VoucherAggregateRootRepository extends CrudRepository<VoucherAggregateRoot, UUID> {
}
