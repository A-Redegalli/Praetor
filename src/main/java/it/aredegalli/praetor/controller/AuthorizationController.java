package it.aredegalli.praetor.controller;

import it.aredegalli.praetor.dto.authorization.AuthorizationRequestDto;
import it.aredegalli.praetor.dto.authorization.AuthorizationResultDto;
import it.aredegalli.praetor.security.context.UserContext;
import it.aredegalli.praetor.service.authorization.AuctoritasService;
import it.aredegalli.praetor.service.user.current.annotation.CurrentUser;
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
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuctoritasService auctoritasService;

    @PostMapping("/authorize")
    public Mono<ResponseEntity<AuthorizationResultDto>> authorize(@CurrentUser UserContext user,
                                                                  @RequestBody @Valid AuthorizationRequestDto request) {
        log.info("[API] authorize request: user={}, request={}", user, request);
        return auctoritasService.authorize(user, request.applicationName(), request.authenticatorName())
                .map(ResponseEntity::ok);
    }
}
