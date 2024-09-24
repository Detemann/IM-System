package com.sarrus.file.controllers;

import com.sarrus.file.dtos.RequestFileDTO;
import com.sarrus.file.dtos.ResponseFileDTO;
import com.sarrus.file.services.file.FileModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileModelService fileModelService;

    @PostMapping
    public ResponseEntity<Object> saveFiles(@Valid RequestFileDTO requestFileDTO) {
        fileModelService.saveAndStore(requestFileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/download")
    public ResponseEntity<List<ResponseFileDTO>> getImage(@Valid @RequestBody RequestFileDTO requestFileDTO) {
            List<ResponseFileDTO> responseFileDTOs = fileModelService.getFiles(requestFileDTO);
            if (!responseFileDTOs.isEmpty()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .body(responseFileDTOs);
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    @PostMapping("/external/{userId}/{playlistId}")
    public ResponseEntity<List<ResponseFileDTO>> postFilesByUserIdAndPlaylistId(@PathVariable Long userId, @PathVariable Long playlistId) {
        List<ResponseFileDTO> files = fileModelService.postFilesById(userId, playlistId);
        if (!files.isEmpty()) {
            return ResponseEntity.ok().body(files);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer fileId) {
        boolean isDeleted = fileModelService.deleteFileById(fileId);
        if (isDeleted) {
            return ResponseEntity.ok().body("Arquivo deletado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}