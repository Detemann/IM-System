package com.sarrus.file.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DataNotFoundException extends NullPointerException {
    private Integer userId;

    public DataNotFoundException(Integer userId, String message) {
        super(message);
        this.userId = userId;
    }

}
