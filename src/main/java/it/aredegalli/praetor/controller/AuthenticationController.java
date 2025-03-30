package it.aredegalli.praetor.controller;

import it.aredegalli.praetor.dto.authentication.changepassword.ChangePasswordDto;
import it.aredegalli.praetor.dto.authentication.login.LoginRequestDto;
import it.aredegalli.praetor.dto.authentication.login.LoginResponseDto;
import it.aredegalli.praetor.dto.authentication.refresh.RefreshRequestDto;
import it.aredegalli.praetor.dto.authentication.refresh.RefreshResponseDto;
import it.aredegalli.praetor.dto.authentication.register.RegisterRequestDto;
import it.aredegalli.praetor.dto.authentication.register.RegisterResponseDto;
import it.aredegalli.praetor.service.authentication.DominatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final DominatusService dominatusService;

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto request) {
        log.info("[API] login request: request={}", request);
        return this.dominatusService.login(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<RefreshResponseDto>> refresh(@RequestBody @Valid RefreshRequestDto request) {
        log.info("[API] refresh request: request={}", request);
        return this.dominatusService.refresh(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<RegisterResponseDto>> register(@RequestBody @Valid RegisterRequestDto request) {
        log.info("[API] register request: request={}", request);
        return this.dominatusService.register(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/change-password")
    public Mono<ResponseEntity<Void>> changePassword(@RequestBody @Valid ChangePasswordDto request) {
        log.info("[API] change password request: request={}", request);
        return this.dominatusService.changePassword(request)
                .thenReturn(ResponseEntity.ok().build());
    }
}
