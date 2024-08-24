package com.sarrus.file.services.file;

import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.exceptions.DataNotFoundException;
import com.sarrus.file.models.FileModel;
import com.sarrus.file.models.Playlist;
import com.sarrus.file.models.User;
import com.sarrus.file.repositories.PlaylistRepository;
import com.sarrus.file.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@NoArgsConstructor
public class FileModelLogic {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;

    public void zipAndStoreFile(FileModel fileModel) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileModel.getFilePath());
        ZipOutputStream zipOutputStream  = new ZipOutputStream(fileOutputStream);
        ZipEntry zipEntry = new ZipEntry(fileModel.getName());

        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(fileModel.getMultipartFile().getBytes());
        zipOutputStream.closeEntry();
        zipOutputStream.close();
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
}
