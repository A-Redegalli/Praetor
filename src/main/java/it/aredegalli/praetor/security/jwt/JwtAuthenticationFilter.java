package it.aredegalli.praetor.security.jwt;

import io.jsonwebtoken.Claims;
import it.aredegalli.praetor.security.context.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtValidator jwtValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtValidator.validate(token)) {
                Claims claims = jwtValidator.getClaims(token);

                UUID userId = UUID.fromString(claims.getSubject());
                String email = claims.get("email", String.class);
                List<String> roles = claims.get("roles", List.class);

                UserContext userContext = UserContext.builder()
                        .userId(userId)
                        .email(email)
                        .roles(roles)
                        .build();

                log.debug("UserContext impostato nel Reactor Context: {}", userContext);
                return chain.filter(exchange)
                        .contextWrite(ctx -> ctx.put(UserContext.class, userContext));
            } else {
                log.warn("JWT non valido ricevuto.");
            }
        }

        return chain.filter(exchange);
    }
}

