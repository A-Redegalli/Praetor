package it.aredegalli.praetor.controller;

import it.aredegalli.praetor.dto.user.UserMeResponse;
import it.aredegalli.praetor.security.context.UserContext;
import it.aredegalli.praetor.service.user.current.annotation.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/praetor/user")
@Slf4j
public class UserController {

    @GetMapping("/me")
    public Mono<ResponseEntity<UserMeResponse>> me(@CurrentUser Mono<UserContext> userMono) {
        return userMono
                .map(user -> new UserMeResponse(user.getUserId(), user.getEmail(), user.getRoles()))
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
