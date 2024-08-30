package com.sarrus.api_files.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    private int status;
    private String message;
    private String imageUrl;

    public ErrorDTO(int status, String message) {
        this.status = status;
        this.message = message;
        this.imageUrl = "https://http.cat/"+status;
    }
}
