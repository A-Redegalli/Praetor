package it.aredegalli.praetor.service.authorization;

import it.aredegalli.auctoritas.dto.authorization.AuthorizationResultDto;
import it.aredegalli.praetor.security.context.UserContext;
import reactor.core.publisher.Mono;

public interface AuctoritasService {
    Mono<AuthorizationResultDto> authorize(UserContext user, String applicationName, String authenticatorName);
}
