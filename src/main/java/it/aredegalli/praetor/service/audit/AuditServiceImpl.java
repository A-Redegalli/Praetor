package it.aredegalli.praetor.service.audit;

import it.aredegalli.praetor.enums.AuditEventTypeEnum;
import it.aredegalli.praetor.model.audit.AuditEventType;
import it.aredegalli.praetor.model.audit.AuditLog;
import it.aredegalli.praetor.repository.audit.AuditEventTypeRepository;
import it.aredegalli.praetor.repository.audit.AuditLogRepository;
import it.aredegalli.praetor.security.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final AuditEventTypeRepository eventTypeRepository;

    @Async
    @Override
    public void logEvent(AuditEventTypeEnum eventEnum, String appName, String description, Map<String, Object> metadata) {
        AuditEventType eventType = eventTypeRepository.findByDescription(eventEnum.name())
                .orElseGet(() -> eventTypeRepository.save(AuditEventType.builder()
                        .description(eventEnum.name())
                        .build()));

        AuditLog audit = AuditLog.builder()
                .eventType(eventType)
                .applicationName(appName)
                .description(description)
                .metadata(metadata)
                .timestamp(Instant.now())
                .build();

        log.debug("[AUDIT] Audit event: {}", audit);
        auditLogRepository.save(audit);
    }

    @Override
    public Map<String, Object> buildMetadata(Map<String, Object> metadata) {
        Map<String, Object> map = metadata != null ? new HashMap<>(metadata) : new HashMap<>();

        ServerHttpRequest request = RequestContextHolder.getRequest();
        if (request != null) {
            request.getRemoteAddress();
            map.put("ip", request.getRemoteAddress().getAddress().getHostAddress());
            map.put("user-agent", request.getHeaders().getFirst("User-Agent"));
        } else {
            map.put("ip", "unknown");
            map.put("user-agent", "unknown");
        }

        return map;
    }

    @Override
    public Map<String, Object> buildMetadata(AuditEventTypeEnum eventEnum, Map<String, Object> metadata) {
        Map<String, Object> map = this.buildMetadata(metadata);
        map.put("event", eventEnum.name());
        return map;
    }

    @Override
    public Map<String, Object> buildMetadata(AuditEventTypeEnum eventEnum) {
        return this.buildMetadata(eventEnum, Map.of());
    }

}
