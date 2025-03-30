package it.aredegalli.praetor.security.jwt;

import io.jsonwebtoken.Claims;
import it.aredegalli.praetor.security.context.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtValidator jwtValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtValidator.validate(token)) {
                Claims claims = jwtValidator.getClaims(token);

                UUID userId = UUID.fromString(claims.getSubject());
                String email = claims.get("email", String.class);

                UserContext userContext = UserContext.builder()
                        .userId(userId)
                        .email(email)
                        .build();

                Map<String, Object> attributes = Map.of(
                        "userId", userId.toString(),
                        "email", email
                );

                BearerTokenAuthentication authentication = getBearerTokenAuthentication(attributes, token, claims);

                log.debug("JWT valido. Autenticazione impostata nel ReactiveSecurityContext.");
                Context context = ReactiveSecurityContextHolder.withAuthentication(authentication);
                context = context.put(UserContext.class, userContext);

                return chain.filter(exchange)
                        .contextWrite(context);

            }

            log.warn("JWT non valido ricevuto.");
        }

        return chain.filter(exchange);
    }

    private static BearerTokenAuthentication getBearerTokenAuthentication(Map<String, Object> attributes, String token, Claims claims) {
        OAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(
                attributes,
                java.util.List.of()
        );

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                TokenType.BEARER,
                token,
                Instant.ofEpochMilli(claims.getIssuedAt().getTime()),
                Instant.ofEpochMilli(claims.getExpiration().getTime())
        );

        return new BearerTokenAuthentication(
                principal,
                accessToken,
                java.util.List.of()
        );
    }
}
