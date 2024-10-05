package com.sarrus.api_files.controllers;

import com.sarrus.api_files.dto.RequestFileDTO;
import com.sarrus.api_files.dto.RequestFileUpdateDTO;
import com.sarrus.api_files.dto.ResponseFileDTO;
import com.sarrus.api_files.service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ResponseFileDTO>> getAllFiles(@PathVariable Integer userId) {
        List<ResponseFileDTO> files = fileService.getAllFiles(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(files);
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@Valid RequestFileDTO requestFileDTO) {
        return fileService.send(requestFileDTO);
    }

    @PostMapping("/download")
    public ResponseEntity<List<ResponseFileDTO>> getFilesByPlaylistId(@Valid @RequestBody RequestFileDTO requestFileDTO) {
        List<ResponseFileDTO> files = fileService.getFilesByPlaylistId(requestFileDTO);
        if (!files.isEmpty()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(files);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<String> updateFile(@Valid RequestFileUpdateDTO requestFileDTO) {
        return fileService.updateFile(requestFileDTO);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") Integer fileId) {
        return fileService.deleteFile(fileId);
    }
}
