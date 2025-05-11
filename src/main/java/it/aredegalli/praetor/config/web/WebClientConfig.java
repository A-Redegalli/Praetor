package it.aredegalli.praetor.config.web;

import it.aredegalli.praetor.config.web.filter.DominatusBearerTokenExchangeFilter;
import it.aredegalli.praetor.config.web.filter.WebClientErrorInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final DominatusBearerTokenExchangeFilter dominatusBearerTokenExchangeFilter;

    @Bean("dominatusClient")
    public WebClient dominatusClient(@Value("${services.dominatus.base-url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .filter(WebClientErrorInterceptor.errorResponseHandler())
                .filter(dominatusBearerTokenExchangeFilter)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("auctoritasClient")
    public WebClient auctoritasClient(@Value("${services.auctoritas.base-url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .filter(WebClientErrorInterceptor.errorResponseHandler())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
