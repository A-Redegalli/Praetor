package it.aredegalli.praetor.dto.authentication.register;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String firstName;
    private String lastName;
}
