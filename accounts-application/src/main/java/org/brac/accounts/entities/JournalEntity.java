package org.brac.accounts.entities;


import jakarta.persistence.Entity;
import org.brac.accounts.events.JournalInfo;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "journal_entities")
public class JournalEntity extends JournalInfo {
}
