package com.sarrus.file.dtos;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record RequestFileUpdateDTO(
        @NotNull
        Integer user,
        Integer playlist,
        Integer time,
        FileDTO file) {
}
