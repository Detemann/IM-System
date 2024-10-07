package com.sarrus.command.handlers;

import com.sarrus.command.dto.ErrorDTO;
import com.sarrus.command.exceptions.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DataExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ErrorDTO exceptionHandler(DataNotFoundException ex) {
        return new ErrorDTO(ex.getMessage(),
                ex.getUserId() == null ? ex.getFileId().toString(): ex.getUserId().toString());
    }
}
