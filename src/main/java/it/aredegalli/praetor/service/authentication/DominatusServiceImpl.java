package it.aredegalli.praetor.service.authentication;

import it.aredegalli.praetor.client.DominatusAuthClient;
import it.aredegalli.praetor.dto.authentication.changepassword.ChangePasswordDto;
import it.aredegalli.praetor.dto.authentication.login.LoginRequestDto;
import it.aredegalli.praetor.dto.authentication.login.LoginResponseDto;
import it.aredegalli.praetor.dto.authentication.refresh.RefreshRequestDto;
import it.aredegalli.praetor.dto.authentication.refresh.RefreshResponseDto;
import it.aredegalli.praetor.dto.authentication.register.RegisterRequestDto;
import it.aredegalli.praetor.dto.authentication.register.RegisterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DominatusServiceImpl implements DominatusService {

    private final DominatusAuthClient dominatusAuthClient;

    @Override
    public Mono<LoginResponseDto> login(LoginRequestDto request) {
        return this.dominatusAuthClient.login(request);
    }

    @Override
    public Mono<RefreshResponseDto> refresh(RefreshRequestDto request) {
        return this.dominatusAuthClient.refresh(request);
    }

    @Override
    public Mono<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto request) {
        return this.dominatusAuthClient.register(request);
    }

    @Override
    public Mono<Void> changePassword(@RequestBody @Valid ChangePasswordDto request) {
        return this.dominatusAuthClient.changePassword(request);
    }

}
