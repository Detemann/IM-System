package com.sarrus.file.services;

import com.sarrus.file.configs.FileStorageConfig;
import com.sarrus.file.dtos.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    private final Path fileStorageLocation;

    public FileService(FileStorageConfig fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getRoot())
                .toAbsolutePath().normalize();
    }

    public void save(FileDTO fileDTO) throws IOException {
        zipAndSaveFile(fileDTO.file());
    }

    private void zipAndSaveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().replaceAll("\\.(png|jpg|mp4)", ".zip");
        Path targetLocation = fileStorageLocation.resolve(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(targetLocation.toString());

        ZipOutputStream zipOutputStream  = new ZipOutputStream(fileOutputStream);
        ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());

        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(file.getBytes());
        zipOutputStream.closeEntry();
        zipOutputStream.close();
    }
}
