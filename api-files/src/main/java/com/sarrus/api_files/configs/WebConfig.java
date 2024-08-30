package com.sarrus.api_files.configs;

import com.sarrus.api_files.dto.ErrorDTO;
import com.sarrus.api_files.exceptions.RetrieveException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8086").defaultCookie("cookieKey", "cookieValue")
                .filter(errorHandlingFilter())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();
    }

    private static ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println(clientResponse.statusCode());
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(ErrorDTO.class)
                        .flatMap(errorBody -> Mono.error(new RetrieveException(clientResponse.statusCode(), errorBody.getMessage())));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }
}
