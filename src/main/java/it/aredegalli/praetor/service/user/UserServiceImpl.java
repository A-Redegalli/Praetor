package it.aredegalli.praetor.service.user;

import it.aredegalli.praetor.dto.user.UserDto;
import it.aredegalli.praetor.security.context.UserContext;
import it.aredegalli.praetor.service.authentication.DominatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DominatusService dominatusService;

    @Override
    public Mono<UserDto> getUserFromContext(UserContext userContext) {
        return this.dominatusService.getUserById(userContext.getUserId())
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .createdAt(user.getCreatedAt())
                        .roles(userContext.getRoles())
                        .build()
                );
    }


}
