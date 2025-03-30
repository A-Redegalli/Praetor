package it.aredegalli.praetor.repository.audit;

import it.aredegalli.praetor.model.audit.AuditEventType;
import it.aredegalli.praetor.model.audit.AuditLog;
import it.aredegalli.praetor.repository.UUIDRepository;

import java.time.Instant;
import java.util.List;

public interface AuditLogRepository extends UUIDRepository<AuditLog> {
    List<AuditLog> findByApplicationName(String applicationName);

    Long deleteByEventTypeAndTimestampBefore(AuditEventType eventType, Instant timestamp);
}
