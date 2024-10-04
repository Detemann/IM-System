package com.sarrus.file.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private Integer fileId;
    private Integer time;
    private String fileName;
    private String fileType;
    private String filePath;
    private String fileContent;

    public FileDTO(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = Base64.getEncoder().encodeToString(fileContent);
    }
}
