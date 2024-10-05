package com.sarrus.command.handlers;

import com.sarrus.command.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PlaylistLogicHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorDTO fileDeleteErrorHandler(RuntimeException ex) {
        return new ErrorDTO(ex.getMessage(), "Internal Server Error");
    }
}
