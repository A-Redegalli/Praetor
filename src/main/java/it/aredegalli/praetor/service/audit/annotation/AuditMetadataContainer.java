package it.aredegalli.praetor.service.audit.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditMetadataContainer {
    AuditMetadata[] value();
}
