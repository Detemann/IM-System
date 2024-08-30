package com.sarrus.file.services.file;

import com.sarrus.file.configs.FileStorageConfig;
import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.models.FileModel;
import com.sarrus.file.repositories.FileRepository;
import com.sarrus.file.services.playlist.PlaylistService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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
        System.out.println(fileDTO);
        for(MultipartFile file: fileDTO.files()) {
            FileModel fileModel = fileModelLogic.populateFileModel(file, fileDTO.user(), fileDTO.playlist());
            fileModel.setFilePath(fileStorageLocation.resolve(file.getOriginalFilename())
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
    }

    public Map<String, byte[]> unzipFiles(Integer userId, Integer fileId) throws IOException {
        return fileModelLogic.unzipFiles(userId, fileId);
    }

    private void save(FileModel fileModel) {
        fileRepository.save(fileModel);
    }
}
