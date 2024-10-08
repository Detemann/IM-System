package com.sarrus.api_files.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record RequestFileDTO(
        Integer user,
        @NotNull
        Integer playlist,
        @NotNull
        Integer time,
        @NotNull//In seconds
        MultipartFile[] files) {
}
