package it.aredegalli.praetor.service.authentication;

import it.aredegalli.dominatus.dto.auth.changepassword.ChangePasswordDto;
import it.aredegalli.dominatus.dto.auth.login.LoginRequestDto;
import it.aredegalli.dominatus.dto.auth.login.LoginResponseDto;
import it.aredegalli.dominatus.dto.auth.refresh.RefreshRequestDto;
import it.aredegalli.dominatus.dto.auth.refresh.RefreshResponseDto;
import it.aredegalli.dominatus.dto.auth.register.RegisterRequestDto;
import it.aredegalli.dominatus.dto.auth.register.RegisterResponseDto;
import it.aredegalli.dominatus.dto.user.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DominatusService {
    Mono<LoginResponseDto> login(LoginRequestDto request);

    Mono<RefreshResponseDto> refresh(RefreshRequestDto request);

    Mono<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto request);

    Mono<Void> changePassword(@RequestBody @Valid ChangePasswordDto request);

    Mono<UserDto> getUserById(UUID userId);
}
