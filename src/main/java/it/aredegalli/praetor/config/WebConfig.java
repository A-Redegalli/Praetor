package it.aredegalli.praetor.config;

import it.aredegalli.praetor.service.user.current.CurrentUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    private final CurrentUserArgumentResolver currentUserArgumentResolver;

    public WebConfig(CurrentUserArgumentResolver resolver) {
        this.currentUserArgumentResolver = resolver;
    }

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(currentUserArgumentResolver);
    }
}
