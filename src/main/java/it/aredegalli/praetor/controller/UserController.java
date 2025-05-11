package it.aredegalli.praetor.controller;

import it.aredegalli.praetor.dto.user.UserDto;
import it.aredegalli.praetor.security.context.UserContext;
import it.aredegalli.praetor.service.user.UserService;
import it.aredegalli.praetor.service.user.current.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/praetor/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public Mono<ResponseEntity<UserDto>> me(@CurrentUser UserContext user, @RequestParam String applicationName) {
        return userService.getUserFromContext(user, applicationName)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
