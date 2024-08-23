package com.sarrus.file.services.file;

import com.sarrus.file.configs.FileStorageConfig;
import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.models.File;
import com.sarrus.file.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private FileRepository fileRepository;
    private FileLogic fileLogic;

    private final Path fileStorageLocation;

    public FileService(FileStorageConfig fileStorageProperties, FileLogic fileLogic) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getRoot())
                .toAbsolutePath().normalize();
        this.fileLogic = fileLogic;
    }

    public void save(FileDTO fileDTO) throws IOException {
        File fileModel = new File(fileDTO, fileStorageLocation);

        fileLogic.zipAndSaveFile(fileModel);
    }
}
