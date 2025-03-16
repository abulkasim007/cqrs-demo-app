package org.brac.accounts.repositories.event;

import java.util.UUID;
import org.brac.accounts.events.VoucherCreatedEvent;
import org.springframework.data.repository.CrudRepository;

public interface VoucherCreatedEventRepository extends CrudRepository<VoucherCreatedEvent, UUID> {
}
