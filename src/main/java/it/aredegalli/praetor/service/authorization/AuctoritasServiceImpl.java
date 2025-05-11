package it.aredegalli.praetor.service.authorization;

import it.aredegalli.auctoritas.dto.authorization.AuthorizationResultDto;
import it.aredegalli.praetor.client.AuctoritasAuthClient;
import it.aredegalli.praetor.security.context.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class AuctoritasServiceImpl implements AuctoritasService {

    private final AuctoritasAuthClient auctoritasClient;

    @Override
    public Mono<AuthorizationResultDto> authorize(UserContext user, String applicationName, String authenticatorName) {
        return auctoritasClient.authorize(applicationName, authenticatorName, user.getUserId().toString())
                .flatMap(result -> {
                    if (result.getRoles() == null || result.getRoles().isEmpty()) {
                        return Mono.error(new AccessDeniedException("Utente non autorizzato"));
                    }

                    UserContext enriched = UserContext.builder()
                            .userId(user.getUserId())
                            .email(user.getEmail())
                            .roles(result.getRoles())
                            .build();

                    return Mono.deferContextual(ctx ->
                            Mono.just(result)
                                    .contextWrite(c -> c.put(UserContext.class, enriched))
                    );
                });
    }

}
