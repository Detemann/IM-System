package com.sarrus.api_files.handlers;

import com.sarrus.api_files.dto.ErrorDTO;
import com.sarrus.api_files.exceptions.RetrieveException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.ConnectException;

@ControllerAdvice
public class RetrieveExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RetrieveException.class)
    public ErrorDTO handleRetrieveException(RetrieveException ex) {
        return new ErrorDTO(
                ex.getStatus(),
                ex.getMessage()
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ConnectException.class)
    public ErrorDTO handleConnectException(ConnectException ex) {
        return new ErrorDTO(
                HttpStatus.SERVICE_UNAVAILABLE,
                ex.getMessage()
        );
    }
}
