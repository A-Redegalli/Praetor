package it.aredegalli.praetor.service.authentication;

import it.aredegalli.dominatus.dto.auth.changepassword.ChangePasswordDto;
import it.aredegalli.dominatus.dto.auth.login.LoginRequestDto;
import it.aredegalli.dominatus.dto.auth.login.LoginResponseDto;
import it.aredegalli.dominatus.dto.auth.refresh.RefreshRequestDto;
import it.aredegalli.dominatus.dto.auth.refresh.RefreshResponseDto;
import it.aredegalli.dominatus.dto.auth.register.RegisterRequestDto;
import it.aredegalli.dominatus.dto.auth.register.RegisterResponseDto;
import it.aredegalli.dominatus.dto.user.UserDto;
import it.aredegalli.praetor.client.DominatusAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
    public Mono<RegisterResponseDto> register(RegisterRequestDto request) {
        return this.dominatusAuthClient.register(request);
    }

    @Override
    public Mono<Void> changePassword(ChangePasswordDto request) {
        return this.dominatusAuthClient.changePassword(request);
    }

    @Override
    public Mono<UserDto> getUserById(UUID userId) {
        return this.dominatusAuthClient.getUserById(userId);
    }

}
