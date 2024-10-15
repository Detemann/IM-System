package com.sarrus.file.services.file;

import com.sarrus.file.dtos.RequestFileDTO;
import com.sarrus.file.dtos.RequestFileUpdateDTO;
import com.sarrus.file.dtos.ResponseFileDTO;
import com.sarrus.file.exceptions.DataNotFoundException;
import com.sarrus.file.models.FileModel;
import com.sarrus.file.models.Playlist;
import com.sarrus.file.models.User;
import com.sarrus.file.repositories.FileRepository;
import com.sarrus.file.repositories.PlaylistRepository;
import com.sarrus.file.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<FileModel> populateFileModel(RequestFileDTO requestFileDTO) {
        List<FileModel> fileModels;
        Optional<User> user = userRepository.findById(requestFileDTO.user());
        if (user.isEmpty()) throw new DataNotFoundException(requestFileDTO.user(), "Usuário não encontrado");

        if (requestFileDTO.playlist() != null) {
            Optional<Playlist> playlist = Optional.of(playlistRepository.findByUserIdAndId(requestFileDTO.user(), requestFileDTO.playlist()).orElse(new Playlist()));

            fileModels = new ArrayList<>();
            Arrays.stream(requestFileDTO.files()).forEach(file -> {
                fileModels.add(new FileModel(file, requestFileDTO.time().intValue(), user.get(), playlist.get()));
            });
        } else {
            fileModels = new ArrayList<>();
            Arrays.stream(requestFileDTO.files()).forEach(file -> {
                fileModels.add(new FileModel(file, requestFileDTO.time().intValue(), user.get()));
            });
        }

        return fileModels;
    }

    public List<ResponseFileDTO> unzipAndPrepResponse(Integer userId) {
        return unzipAndPrepResponse(userId, null);
    }

    public List<ResponseFileDTO> unzipAndPrepResponse(Integer userId, Integer playlistId) {
        List<FileModel> files;
        if (playlistId != null) {
            files = fileRepository.findByPlaylistAndUser(playlistId, userId);
        } else {
            files = fileRepository.getAllFiles(userId);
        }
        if (files.isEmpty()) {
            throw new DataNotFoundException(userId, "Files not found!");
        }

        List<ResponseFileDTO> responseFileDTOS = new ArrayList<>();
        files.forEach(file -> {
            try {
                FileInputStream zipFile = new FileInputStream(file.getFilePath());
                ZipInputStream zip = new ZipInputStream(zipFile);
                zip.getNextEntry();

                responseFileDTOS.add(new ResponseFileDTO(file.getId(), file.getName(), file.getPlaylist() != null ? file.getPlaylist().getId():null, file.getTime(), zip.readAllBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return responseFileDTOS;
    }

    public FileModel updateFile(RequestFileUpdateDTO requestFileUpdateDTO) {
        try {
            Optional<Playlist> playlist = requestFileUpdateDTO.playlist() != null ? playlistRepository.findById(requestFileUpdateDTO.playlist()): Optional.empty();
            Optional<FileModel> savedFile = fileRepository.findById(requestFileUpdateDTO.file().getFileId());
            if (savedFile.isEmpty()) throw new DataNotFoundException("Arquivo não encontrado", requestFileUpdateDTO.file().getFileId());

            FileModel fileModel = savedFile.get();
            fileModel.setPlaylist(playlist.orElse(null));
            fileModel.setTime(requestFileUpdateDTO.time());

            return fileModel;
        } catch (Exception e) {
            throw new RuntimeException("[FileModelService -> updateFile] " + e.getMessage());
        }
    }
}
