package it.aredegalli.praetor.dto.authorization;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public record AuthorizationRequestDto(
        @NotBlank String applicationName,
        @NotBlank String authenticatorName
) {
}
