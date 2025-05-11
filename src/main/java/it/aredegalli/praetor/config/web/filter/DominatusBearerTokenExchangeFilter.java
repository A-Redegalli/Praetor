package it.aredegalli.praetor.config.web.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
public class DominatusBearerTokenExchangeFilter implements ExchangeFilterFunction {

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        String path = request.url().getPath();

        boolean needsAuth = path.startsWith("/users/");

        if (!needsAuth) {
            return next.exchange(request);
        }

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth instanceof BearerTokenAuthentication)
                .map(auth -> (BearerTokenAuthentication) auth)
                .map(BearerTokenAuthentication::getToken)
                .map(OAuth2AccessToken::getTokenValue)
                .flatMap(token -> {
                    ClientRequest.Builder builder = ClientRequest.from(request);
                    builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                    return next.exchange(builder.build());
                })
                .switchIfEmpty(next.exchange(request));


    }
}

