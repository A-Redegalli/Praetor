package it.aredegalli.praetor.client;

import it.aredegalli.praetor.dto.authentication.changepassword.ChangePasswordDto;
import it.aredegalli.praetor.dto.authentication.login.LoginRequestDto;
import it.aredegalli.praetor.dto.authentication.login.LoginResponseDto;
import it.aredegalli.praetor.dto.authentication.refresh.RefreshRequestDto;
import it.aredegalli.praetor.dto.authentication.refresh.RefreshResponseDto;
import it.aredegalli.praetor.dto.authentication.register.RegisterRequestDto;
import it.aredegalli.praetor.dto.authentication.register.RegisterResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DominatusAuthClient {

    private final WebClient webClient;

    public DominatusAuthClient(@Qualifier("dominatusClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<LoginResponseDto> login(LoginRequestDto request) {
        return webClient.post()
                .uri("/auth/login")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LoginResponseDto.class);
    }

    public Mono<RefreshResponseDto> refresh(RefreshRequestDto request) {
        return webClient.post()
                .uri("/auth/refresh")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RefreshResponseDto.class);
    }

    public Mono<RegisterResponseDto> register(RegisterRequestDto request) {
        return webClient.post()
                .uri("/auth/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RegisterResponseDto.class);
    }

    public Mono<Void> changePassword(ChangePasswordDto request) {
        return webClient.post()
                .uri("/auth/change-password")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class);
    }
}

