package it.aredegalli.praetor.service.user;

import it.aredegalli.praetor.dto.user.UserDto;
import it.aredegalli.praetor.security.context.UserContext;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDto> getUserFromContext(UserContext userContext, String applicationName);
}
