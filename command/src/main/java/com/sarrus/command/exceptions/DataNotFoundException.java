package com.sarrus.command.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DataNotFoundException extends NullPointerException {
    private Integer userId;
    private Integer fileId;

    public DataNotFoundException(Integer userId, String message) {
        super(message);
        this.userId = userId;
    }

    public DataNotFoundException(String message, Integer fileId) {
        super(message);
        this.fileId = fileId;
    }
}
