package it.aredegalli.praetor.client;

import it.aredegalli.auctoritas.dto.authorization.AuthorizationResultDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuctoritasAuthClient {

    private final WebClient webClient;

    public AuctoritasAuthClient(@Qualifier("auctoritasClient") WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<AuthorizationResultDto> authorize(String applicationName, String authenticatorName, String externalUserId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/authorization")
                        .queryParam("applicationName", applicationName)
                        .queryParam("authenticatorName", authenticatorName)
                        .queryParam("externalUserId", externalUserId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AuthorizationResultDto.class);
    }
}
