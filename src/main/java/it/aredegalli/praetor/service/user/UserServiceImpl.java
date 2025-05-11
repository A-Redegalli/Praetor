package it.aredegalli.praetor.service.user;

import it.aredegalli.auctoritas.dto.authorization.AuthorizationResultDto;
import it.aredegalli.praetor.dto.user.UserDto;
import it.aredegalli.praetor.security.context.UserContext;
import it.aredegalli.praetor.service.authentication.DominatusService;
import it.aredegalli.praetor.service.authorization.AuctoritasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DominatusService dominatusService;
    private final AuctoritasService auctoritasService;

    @Override
    public Mono<UserDto> getUserFromContext(UserContext userContext, String applicationName, String authenticatorName) {
        Mono<Map<UUID, String>> rolesMono = auctoritasService.authorize(userContext, applicationName, authenticatorName)
                .map(AuthorizationResultDto::getRoles)
                .defaultIfEmpty(Map.of());

        return Mono.zip(
                dominatusService.getUserById(userContext.getUserId()),
                rolesMono
        ).map(tuple -> {
            var user = tuple.getT1();
            var roles = tuple.getT2();
            return UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .createdAt(user.getCreatedAt())
                    .roles(roles)
                    .build();
        });
    }


}
