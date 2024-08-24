package com.sarrus.file.services.file;

import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.exceptions.DataNotFoundException;
import com.sarrus.file.models.FileModel;
import com.sarrus.file.models.Playlist;
import com.sarrus.file.models.User;
import com.sarrus.file.repositories.PlaylistRepository;
import com.sarrus.file.repositories.FileRepository;
import com.sarrus.file.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
@NoArgsConstructor
public class FileModelLogic {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private FileRepository fileRepository;

    public void zipAndStoreFile(FileModel fileModel) throws IOException {
        ZipOutputStream zipOutputStream;

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileModel.getFilePath())) {
            zipOutputStream = new ZipOutputStream(fileOutputStream);

            ZipEntry zipEntry = new ZipEntry(fileModel.getName());

            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(fileModel.getMultipartFile().getBytes());
            zipOutputStream.closeEntry();
            zipOutputStream.close();
        } catch (IOException e) {
            throw new IOException("[FIleModelLogic -> zipAndStoreFile] Internal Server Error");
        }
    }

    public FileModel populateFileModel(FileDTO fileDTO, int userId, int playlistId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(userId, "User not found!")));

        Optional<Playlist> playlist = Optional.of(playlistRepository.findByUserIdAndId(userId, playlistId)
                .orElse(new Playlist()));

        FileModel fileModel = new FileModel(fileDTO);
        fileModel.setUser(user.get());
        fileModel.setPlaylist(playlist.get());

        return fileModel;
    }

    public Map<String, byte[]> unzipFiles(Integer userId, Integer fileId) throws IOException {
        Optional<FileModel> zipFilePath = Optional.ofNullable(fileRepository.findById(fileId).orElseThrow(() -> new DataNotFoundException(fileId, "Path not found")));

        Map<String, byte[]> unzippedFiles = new HashMap<>();

        try (FileInputStream fileInputStream = new FileInputStream(zipFilePath.get().getFilePath());
             ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                StreamUtils.copy(zipInputStream, outputStream);
                unzippedFiles.put(zipEntry.getName(), outputStream.toByteArray());
                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            throw new IOException("[FileModelLogic -> unzipFiles] Internal Server Error", e);
        }

        return unzippedFiles;
    }
}
