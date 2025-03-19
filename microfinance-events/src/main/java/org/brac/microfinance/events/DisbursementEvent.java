package org.brac.microfinance.events;


import jakarta.persistence.Entity;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "disbursement_events")
public class DisbursementEvent extends DisbursementInfo {
}