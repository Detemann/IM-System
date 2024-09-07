package com.sarrus.file.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record RequestFileDTO(
        @NotNull
        Integer user,
        @NotNull
        Integer playlist,
        Integer time,
        MultipartFile[] files) {
}
