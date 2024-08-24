package com.sarrus.file.handlers;

import com.sarrus.file.dtos.ErrorDTO;
import com.sarrus.file.exceptions.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DataExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorDTO exceptionHandler(DataNotFoundException ex) {
        return new ErrorDTO(ex.getMessage(),
                ex.getUserId().toString());
    }
}
