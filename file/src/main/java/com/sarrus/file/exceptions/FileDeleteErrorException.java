package com.sarrus.file.exceptions;

import lombok.Getter;


@Getter
public class FileDeleteErrorException extends RuntimeException {
    private String detail;

    public FileDeleteErrorException(String message, String detail) {
        super(message);
        this.detail = detail;
    }
}
