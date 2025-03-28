package it.aredegalli.praetor.dto.authentication.refresh;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshResponseDto {
    private String accessToken;
    private String refreshToken;
}