package com.sarrus.api_files.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
public class FileDTO {
    String fileName;
    String fileContent;

    public FileDTO(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = Base64.getEncoder().encodeToString(fileContent);
    }
}
