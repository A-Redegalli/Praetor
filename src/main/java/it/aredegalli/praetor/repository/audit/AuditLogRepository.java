package it.aredegalli.praetor.repository.audit;

import it.aredegalli.praetor.model.audit.AuditEventType;
import it.aredegalli.praetor.model.audit.AuditLog;
import it.aredegalli.praetor.repository.UUIDRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends UUIDRepository<AuditLog> {
    List<AuditLog> findByUserId(UUID userId);

    List<AuditLog> findByApplicationName(String applicationName);

    Long deleteByEventTypeAndTimestampBefore(AuditEventType eventType, Instant timestamp);
}
