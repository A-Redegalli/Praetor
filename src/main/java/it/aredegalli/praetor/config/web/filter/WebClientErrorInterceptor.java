package it.aredegalli.praetor.config.web.filter;

import it.aredegalli.praetor.dto.error.ErrorResponse;
import it.aredegalli.praetor.exception.RemoteServiceException;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class WebClientErrorInterceptor {

    public static ExchangeFilterFunction errorResponseHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(ErrorResponse.class)
                        .flatMap(error -> {
                            String code = error.getCode() != null ? error.getCode() : "REMOTE_ERROR";
                            String msg = error.getMessage() != null ? error.getMessage() : "Errore remoto senza messaggio";
                            return Mono.error(new RemoteServiceException(code, msg));
                        });
            }
            return Mono.just(clientResponse);
        });
    }
}
