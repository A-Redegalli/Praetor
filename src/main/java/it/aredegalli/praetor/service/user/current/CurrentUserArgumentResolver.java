package it.aredegalli.praetor.service.user.current;

import it.aredegalli.praetor.security.context.UserContext;
import it.aredegalli.praetor.service.user.current.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && UserContext.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext context, ServerWebExchange exchange) {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey(UserContext.class)) {
                return Mono.just(ctx.get(UserContext.class));
            }
            return Mono.empty();
        });
    }
}
