package com.sarrus.file.dtos;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record FileDTO(
        Integer user,
        @NotNull
        Integer playlist,
        @NotNull
        Integer time,
        MultipartFile[] files) {
}
