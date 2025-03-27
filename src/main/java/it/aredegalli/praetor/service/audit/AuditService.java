package it.aredegalli.praetor.service.audit;

import it.aredegalli.praetor.enums.AuditEventTypeEnum;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * Service interface for handling audit events.
 */
public interface AuditService {

    /**
     * Logs an audit event asynchronously.
     *
     * @param eventEnum   the type of audit event
     * @param appName     the name of the application where the event occurred
     * @param description a description of the event
     * @param metadata    additional metadata related to the event
     */
    @Async
    void logEvent(AuditEventTypeEnum eventEnum, String appName, String description, Map<String, Object> metadata);

    /**
     * Builds metadata for an audit event.
     *
     * @param metadata the initial metadata
     * @return the built metadata
     */
    Map<String, Object> buildMetadata(Map<String, Object> metadata);

    /**
     * Builds metadata for an audit event with a specific event type.
     *
     * @param eventEnum the type of audit event
     * @param metadata  the initial metadata
     * @return the built metadata
     */
    Map<String, Object> buildMetadata(AuditEventTypeEnum eventEnum, Map<String, Object> metadata);

    /**
     * Builds metadata for an audit event with a specific event type.
     *
     * @param eventEnum the type of audit event
     * @return the built metadata
     */
    Map<String, Object> buildMetadata(AuditEventTypeEnum eventEnum);
}
