package com.sarrus.file.services.file;

import com.sarrus.file.configs.FileStorageConfig;
import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.models.FileModel;
import com.sarrus.file.repositories.FileRepository;
import com.sarrus.file.services.playlist.PlaylistService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileModelService {

    private FileRepository fileRepository;
    private FileModelLogic fileModelLogic;
    private PlaylistService playlistService;
    private final Path fileStorageLocation;

    public FileModelService(FileStorageConfig fileStorageProperties, FileModelLogic fileModelLogic, FileRepository fileRepository, PlaylistService playlistService) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getRoot())
                .toAbsolutePath().normalize();
        this.fileModelLogic = fileModelLogic;
        this.fileRepository = fileRepository;
        this.playlistService = playlistService;
    }

    public void saveAndStore(FileDTO fileDTO) throws IOException {
        FileModel fileModel = fileModelLogic.populateFileModel(fileDTO, fileDTO.user(), fileDTO.playlist());
        fileModel.setFilePath(fileStorageLocation.resolve(fileDTO.file().getOriginalFilename())
                .toString()
                .replaceAll("\\.(png|jpg|mp4)", ".zip"));

        fileModelLogic.zipAndStoreFile(fileModel);
        if (fileModel.getPlaylist().getId() == null) {
            //Todo alinhar como deve ser gerado o nome da playlist
            fileModel.getPlaylist().setName("Playlist "+fileModel.getUser().getName());
            fileModel.getPlaylist().setUserId(fileModel.getUser());
        }
        playlistService.save(fileModel.getPlaylist());
        this.save(fileModel);
    }

    private void save(FileModel fileModel) {
        fileRepository.save(fileModel);
    }
}
