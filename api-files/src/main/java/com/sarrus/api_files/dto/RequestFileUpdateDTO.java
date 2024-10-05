package com.sarrus.api_files.dto;

import jakarta.validation.constraints.NotNull;

public record RequestFileUpdateDTO(
        @NotNull
        Integer user,
        Integer playlist,
        Integer time,
        FileDTO file) {
}
