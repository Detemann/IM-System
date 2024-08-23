package com.sarrus.file.services.file;

import com.sarrus.file.models.File;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@NoArgsConstructor
public class FileLogic {

    public void zipAndSaveFile(File fileModel) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileModel.getFilePath());
        ZipOutputStream zipOutputStream  = new ZipOutputStream(fileOutputStream);
        ZipEntry zipEntry = new ZipEntry(fileModel.getName());

        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(fileModel.getMultipartFile().getBytes());
        zipOutputStream.closeEntry();
        zipOutputStream.close();
    }
}
