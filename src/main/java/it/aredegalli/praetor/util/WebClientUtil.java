package it.aredegalli.praetor.util;

import it.aredegalli.praetor.dto.error.ErrorResponse;
import it.aredegalli.praetor.exception.RemoteServiceException;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class WebClientUtil {

    public static <T> Function<ClientResponse, Mono<? extends Throwable>> handleRemoteError(Class<T> errorType) {
        return clientResponse -> clientResponse
                .bodyToMono(errorType)
                .map(error -> {
                    if (error instanceof ErrorResponse err) {
                        return new RemoteServiceException(err.getCode(), err.getMessage());
                    }
                    return new RemoteServiceException("REMOTE_ERROR", clientResponse.statusCode() + " " + error.toString());
                });
    }

}
