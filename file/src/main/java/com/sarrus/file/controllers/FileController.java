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

    private final FileModelService fileModelService;

    public FileController(FileModelService fileModelService) {
        this.fileModelService = fileModelService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ResponseFileDTO>> getAllFiles(@PathVariable Integer userId) {
        List<ResponseFileDTO> files = fileModelService.getAllFiles(userId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

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

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer fileId) {
        if (fileModelService.deleteFileById(fileId)) {
            return ResponseEntity.ok().body("Arquivo deletado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}