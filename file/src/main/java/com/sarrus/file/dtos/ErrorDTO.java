package com.sarrus.file.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    private String message;
    private String value;

    public ErrorDTO(String message, String details) {
        this.message = message;
        this.value = details;
    }
}