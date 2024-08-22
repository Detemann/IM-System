package com.sarrus.api_files.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
public class RetrieveException extends RuntimeException {
    private HttpStatusCode status;

    public RetrieveException(HttpStatusCode status,String message) {
        super(message);
        this.status = status;
    }
}
