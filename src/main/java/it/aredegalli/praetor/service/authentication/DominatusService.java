package it.aredegalli.praetor.service.authentication;

import it.aredegalli.praetor.dto.authentication.changepassword.ChangePasswordDto;
import it.aredegalli.praetor.dto.authentication.login.LoginRequestDto;
import it.aredegalli.praetor.dto.authentication.login.LoginResponseDto;
import it.aredegalli.praetor.dto.authentication.refresh.RefreshRequestDto;
import it.aredegalli.praetor.dto.authentication.refresh.RefreshResponseDto;
import it.aredegalli.praetor.dto.authentication.register.RegisterRequestDto;
import it.aredegalli.praetor.dto.authentication.register.RegisterResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface DominatusService {
    Mono<LoginResponseDto> login(LoginRequestDto request);

    Mono<RefreshResponseDto> refresh(RefreshRequestDto request);

    Mono<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto request);

    Mono<Void> changePassword(@RequestBody @Valid ChangePasswordDto request);
}
