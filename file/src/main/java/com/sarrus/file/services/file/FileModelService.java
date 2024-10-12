package com.sarrus.file.services.file;

import com.sarrus.file.configs.FileStorageConfig;
import com.sarrus.file.dtos.RequestFileDTO;
import com.sarrus.file.dtos.RequestFileUpdateDTO;
import com.sarrus.file.dtos.ResponseFileDTO;
import com.sarrus.file.exceptions.DataNotFoundException;
import com.sarrus.file.exceptions.FileDeleteErrorException;
import com.sarrus.file.models.FileModel;
import com.sarrus.file.models.Playlist;
import com.sarrus.file.models.User;
import com.sarrus.file.repositories.FileRepository;
import com.sarrus.file.repositories.PlaylistRepository;
import com.sarrus.file.repositories.UserRepository;
import com.sarrus.file.services.playlist.PlaylistService;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FileModelService {

    private final FileRepository fileRepository;
    private final FileModelLogic fileModelLogic;
    private final PlaylistService playlistService;
    private final Path fileStorageLocation;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;

    public FileModelService(FileStorageConfig fileStorageProperties,
                            FileModelLogic fileModelLogic,
                            FileRepository fileRepository,
                            PlaylistService playlistService,
                            UserRepository userRepository, PlaylistRepository playlistRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getRoot())
                .toAbsolutePath().normalize();
        this.fileModelLogic = fileModelLogic;
        this.fileRepository = fileRepository;
        this.playlistService = playlistService;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
    }

    public void saveAndStore(RequestFileDTO requestFileDTO) {
        List<FileModel> fileModels = fileModelLogic.populateFileModel(requestFileDTO);
        fileModels.forEach(file -> {
            file.setFilePath(fileStorageLocation.resolve(file.getName())
                    .toString()
                    .replaceAll("\\.(png|jpg|mp4|jpeg|txt|html)", ".zip"));

            try {
                fileModelLogic.zipAndStoreFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (file.getPlaylist() != null) playlistService.save(file.getPlaylist());
            this.saveFile(file);
        });
    }

    public List<ResponseFileDTO> getFiles(RequestFileDTO requestFileDTO) {
        return fileModelLogic.unzipAndPrepResponse(requestFileDTO.user(), requestFileDTO.playlist());
    }

    public boolean deleteFileById(Integer fileId) {
        try {
            Optional<FileModel> fileModel = fileRepository.findById(fileId);
            if (fileModel.isEmpty()) throw new DataNotFoundException("Arquivo não encontrado", fileId);

            Path file = Paths.get(fileModel.get().getFilePath());

            Files.delete(file);
            this.deleteFile(null, fileModel.get());

        } catch (Exception e) {
            throw new FileDeleteErrorException(e.getMessage(), "[FileModelService -> deleteFileById] Erro ao excluir o arquivo");
        }
        return true;
    }

    public void updateFileById(RequestFileUpdateDTO requestFileDTO) {
        Optional<User> fileOwner = userRepository.findById(requestFileDTO.user());
        if (fileOwner.isEmpty()) throw new DataNotFoundException(requestFileDTO.user(), "Usuário não encontrado");

        FileModel file = fileModelLogic.updateFile(requestFileDTO);
        this.saveFile(file);
    }

    public List<ResponseFileDTO> getAllFiles(Integer userId) {
        return fileModelLogic.unzipAndPrepResponse(userId);
    }


    private void saveFile(FileModel fileModel) {
        fileRepository.save(fileModel);
    }

    private void deleteFile(Integer fileId, FileModel fileModel) {
        if(fileModel != null) {
            fileRepository.delete(fileModel);
        } else {
            fileRepository.deleteById(fileId);
        }
    }

}
