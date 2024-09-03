package com.sarrus.file.services.file;

import com.sarrus.file.enums.ContentTypes;
import com.sarrus.file.exceptions.DataNotFoundException;
import com.sarrus.file.models.FileModel;
import com.sarrus.file.models.Playlist;
import com.sarrus.file.models.User;
import com.sarrus.file.repositories.FileRepository;
import com.sarrus.file.repositories.PlaylistRepository;
import com.sarrus.file.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.*;
import java.util.List;
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

    public FileModel populateFileModel(MultipartFile file, int userId, int playlistId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(userId, "User not found!")));

        Optional<Playlist> playlist = Optional.of(playlistRepository.findByUserIdAndId(userId, playlistId)
                .orElse(new Playlist()));

        FileModel fileModel = new FileModel(file);
        fileModel.setUser(user.get());
        fileModel.setPlaylist(playlist.get());

        return fileModel;
    }

    public MultiValueMap<String, HttpEntity<?>> unzipFiles(Integer userId, Integer playlistId) {
        List<Optional<FileModel>> zipFile = fileRepository.findByPlaylistAndUser(playlistId, userId);

        MultipartBodyBuilder multipartBuilder = new MultipartBodyBuilder();

        zipFile.forEach(file -> {
            try (FileInputStream fileInputStream = new FileInputStream(file.get().getFilePath())) {
                byte[] bytes = new ZipInputStream(fileInputStream).readAllBytes();
                multipartBuilder.part(file.get().getName(), new ByteArrayResource(bytes))
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "inline; filename=\"" + UriUtils.encodePath(file.get().getName(), "UTF-8") + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, ContentTypes.getByValue(file.get().getFileType()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return multipartBuilder.build();
    }
}
