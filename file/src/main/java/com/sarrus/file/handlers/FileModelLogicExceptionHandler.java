package com.sarrus.file.handlers;

import com.sarrus.file.dtos.ErrorDTO;
import com.sarrus.file.exceptions.FileDeleteErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class FileModelLogicExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public ErrorDTO iOException(IOException ex) {
        return new ErrorDTO(ex.getMessage(), "Internal Server Error");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileDeleteErrorException.class)
    public ErrorDTO fileDeleteErrorHandler(FileDeleteErrorException ex) {
        return new ErrorDTO(ex.getMessage(), ex.getDetail());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorDTO fileDeleteErrorHandler(RuntimeException ex) {
        return new ErrorDTO(ex.getMessage(), "Internal Server Error");
    }
}
