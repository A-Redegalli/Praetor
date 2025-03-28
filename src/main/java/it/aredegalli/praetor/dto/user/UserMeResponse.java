package it.aredegalli.praetor.dto.user;

import java.util.List;
import java.util.UUID;

public record UserMeResponse(
        UUID userId,
        String email,
        List<String> roles
) {
}
