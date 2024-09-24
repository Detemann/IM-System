package com.sarrus.file.services.file;

import com.sarrus.file.configs.FileStorageConfig;
import com.sarrus.file.dtos.RequestFileDTO;
import com.sarrus.file.dtos.ResponseFileDTO;
import com.sarrus.file.models.FileModel;
import com.sarrus.file.repositories.FileRepository;
import com.sarrus.file.services.playlist.PlaylistService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class FileModelService {

    private FileRepository fileRepository;
    private FileModelLogic fileModelLogic;
    private PlaylistService playlistService;
    private final Path fileStorageLocation;
    private final WebClient webClient;
    public FileModelService(FileStorageConfig fileStorageProperties,
                            FileModelLogic fileModelLogic,
                            FileRepository fileRepository,
                            PlaylistService playlistService,
                            WebClient.Builder webClientBuilder) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getRoot())
                .toAbsolutePath().normalize();
        this.fileModelLogic = fileModelLogic;
        this.fileRepository = fileRepository;
        this.playlistService = playlistService;
        this.webClient = webClientBuilder.baseUrl("bota a url da api q eu n to lembrando nessa pora").build();
    }

    public void saveAndStore(RequestFileDTO requestFileDTO) {
        List<FileModel> fileModels = fileModelLogic.populateFileModel(requestFileDTO);
        fileModels.forEach(file -> {
           file.setFilePath(fileStorageLocation.resolve(file.getName())
                   .toString()
                   .replaceAll("\\.(png|jpg|mp4|jpeg)", ".zip"));

            try {
                fileModelLogic.zipAndStoreFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (file.getPlaylist() == null) {
                file.getPlaylist().setName("Playlist " + file.getUser().getName());
                file.getPlaylist().setUserId(file.getUser());
            }
            playlistService.save(file.getPlaylist());
            this.saveFile(file);
        });
    }

    public List<ResponseFileDTO> getFiles(RequestFileDTO requestFileDTO) {
        return fileModelLogic.unzipAndPrepResponse(requestFileDTO.user(), requestFileDTO.playlist());
    }

    public List<ResponseFileDTO> postFilesById(Long userId, Long playlistId) {
        return webClient.post()
                .uri("/file/{userId}/{playlistId}", userId, playlistId)
                .retrieve()
                .bodyToFlux(ResponseFileDTO.class)
                .collectList()
                .block();
    }

    public boolean deleteFileById(Integer fileId) {
        try {
            webClient.delete()
                    .uri("/file/{fileId}", fileId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private void saveFile(FileModel fileModel) {
        fileRepository.save(fileModel);
    }

    public void deleteFile(Integer fileId) {
        fileRepository.deleteById(fileId);
    }

}
