package it.aredegalli.praetor.enums;

public enum AuditEventTypeEnum {

    // === Sicurezza ===
    INVALID_JWT,              // Token non valido o expired
    ACCESS_DENIED,            // Accesso respinto per mancanza di permessi
    RATE_LIMITED,             // Richiesta bloccata da rate limiter
    IP_BLOCKED,               // IP in blacklist

    // === Proxy / Forwarding ===
    FORWARDING_FAILURE,       // Fallita comunicazione con Dominatus/Auctoritas
    FORWARDING_TIMEOUT,       // Timeout durante forwarding
    CIRCUIT_BREAK_OPEN,       // Chiamata rifiutata per resilienza attiva

    // === Mapping / Identity ===
    MAPPING_NOT_FOUND,        // Mapping user <-> authenticator mancante
    MAPPING_INCONSISTENT,     // Inconsistenza tra Dominatus e Auctoritas

    // === Refresh / Sessioni ===
    REFRESH_REUSE_DETECTED,   // Stesso refresh token usato due volte (possibile furto)
    REFRESH_TOKEN_REJECTED,   // Refresh fallito (token invalidato o malformato)

    // === Sistema ===
    SYSTEM_ERROR,             // Errore interno inatteso
    METADATA_EXTRACTION_ERROR,// Fallimento nella raccolta metadati per audit
    UNEXPECTED_EXCEPTION,      // Qualsiasi eccezione non gestita propagata

}

