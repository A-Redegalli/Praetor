package it.aredegalli.praetor.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class RequestContextHolder implements WebFilter {

    private static final ThreadLocal<ServerHttpRequest> requestThreadLocal = new ThreadLocal<>();

    public static ServerHttpRequest getRequest() {
        return requestThreadLocal.get();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        requestThreadLocal.set(exchange.getRequest());
        return chain.filter(exchange)
                .doFinally(signal -> requestThreadLocal.remove());
    }
}
