package it.aredegalli.praetor.repository.audit;

import it.aredegalli.praetor.model.audit.AuditEventType;
import it.aredegalli.praetor.repository.UUIDRepository;

import java.util.Optional;

public interface AuditEventTypeRepository extends UUIDRepository<AuditEventType> {
    Optional<AuditEventType> findByDescription(String description);

}
