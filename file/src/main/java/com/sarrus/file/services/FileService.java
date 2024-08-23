package com.sarrus.file.services;

import com.sarrus.file.configs.FileStorageConfig;
import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.models.File;
import com.sarrus.file.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    private final Path fileStorageLocation;

    public FileService(FileStorageConfig fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getRoot())
                .toAbsolutePath().normalize();
    }

    public void save(FileDTO fileDTO) throws IOException {
        File fileModel = new File(fileDTO, fileStorageLocation);

        zipAndSaveFile(fileModel);
    }

    private void zipAndSaveFile(File fileModel) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileModel.getFilePath());
        ZipOutputStream zipOutputStream  = new ZipOutputStream(fileOutputStream);
        ZipEntry zipEntry = new ZipEntry(fileModel.getName());

        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(fileModel.getMultipartFile().getBytes());
        zipOutputStream.closeEntry();
        zipOutputStream.close();
    }
}
