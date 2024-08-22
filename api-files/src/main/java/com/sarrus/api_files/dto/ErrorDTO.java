package com.sarrus.api_files.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class ErrorDTO {
    private int status;
    private String message;
    private String imageUrl;

    public ErrorDTO(HttpStatusCode status, String message) {
        this.status = status.value();
        this.message = message;
        this.imageUrl = "https://http.cat/"+status.value();
    }
}
