package org.brac.accounts.repositories.event;

import java.util.UUID;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VoucherCreatedEventRepository extends ReactiveCrudRepository<VoucherCreatedEvent, UUID> {
}
