package it.aredegalli.praetor.service.audit.annotation;

import it.aredegalli.praetor.enums.AuditEventTypeEnum;

import java.lang.annotation.*;

/**
 * Annotation to mark methods for auditing.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Audit {
    /**
     * Specifies the type of audit event.
     *
     * @return the audit event type
     */
    AuditEventTypeEnum event();

    /**
     * Specifies the application name.
     * Default is "Auctoritas".
     *
     * @return the application name
     */
    String appName() default "Auctoritas";

    /**
     * Provides a description for the audit event.
     *
     * @return the description
     */
    String description();

    /**
     * Indicates whether to include call parameters in the audit.
     * Default is true.
     *
     * @return true if call parameters should be included, false otherwise
     */
    boolean callParams() default true;
}


