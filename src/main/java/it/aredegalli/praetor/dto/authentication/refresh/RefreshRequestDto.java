package it.aredegalli.praetor.dto.authentication.refresh;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshRequestDto {

    @NotBlank(message = "Refresh token is mandatory")
    private String refreshToken;
}
