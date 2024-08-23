package com.sarrus.file.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class ErrorDTO {
    private int status;
    private String message;

    public ErrorDTO(HttpStatusCode status, String message) {
        this.status = status.value();
        this.message = message;
    }
}