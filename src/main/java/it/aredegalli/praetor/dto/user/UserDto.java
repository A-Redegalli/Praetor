package it.aredegalli.praetor.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private Instant createdAt;
}