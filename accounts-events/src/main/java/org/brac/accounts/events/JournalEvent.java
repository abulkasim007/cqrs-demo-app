package org.brac.accounts.events;

import jakarta.persistence.Entity;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "journal_events")
public class JournalEvent extends JournalInfo {
}
