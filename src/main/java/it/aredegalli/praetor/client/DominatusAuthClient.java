package it.aredegalli.praetor.client;

import it.aredegalli.dominatus.dto.auth.changepassword.ChangePasswordDto;
import it.aredegalli.dominatus.dto.auth.login.LoginRequestDto;
import it.aredegalli.dominatus.dto.auth.login.LoginResponseDto;
import it.aredegalli.dominatus.dto.auth.refresh.RefreshRequestDto;
import it.aredegalli.dominatus.dto.auth.refresh.RefreshResponseDto;
import it.aredegalli.dominatus.dto.auth.register.RegisterRequestDto;
import it.aredegalli.dominatus.dto.auth.register.RegisterResponseDto;
import it.aredegalli.dominatus.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

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

    public Mono<UserDto> getUserById(UUID userId) {
        return Mono.deferContextual(ctx -> {
            Authentication authentication = ctx.get(Authentication.class);

            if (authentication instanceof BearerTokenAuthentication bearer) {
                String token = bearer.getToken().getTokenValue();

                return webClient.get()
                        .uri("/users/{userId}", userId)
                        .headers(headers -> headers.setBearerAuth(token))
                        .retrieve()
                        .bodyToMono(UserDto.class);
            }

            return Mono.error(new IllegalStateException("No BearerTokenAuthentication found in context"));
        });
    }

}

