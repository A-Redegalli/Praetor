package it.aredegalli.praetor.security.context;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserContext {
    private UUID userId;
    private String email;
}
