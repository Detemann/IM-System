package com.sarrus.file.controllers;

import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.services.file.FileModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class FileController {

    @Autowired
    private FileModelService fileModelService;

    @PostMapping
    public ResponseEntity<Object> test(@Valid FileDTO fileDTO) throws IOException {
        fileModelService.saveAndStore(fileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

/*
* Todo
*  Eliminar esse endpoint e fazer um novo onde seja retornado todos os arquivos de uma playlist
*  Obs.: VÁ PARA O CONTROLLER DO api-files para mais instruções
* */
    @GetMapping("/{userId}&{fileId}")
    public ResponseEntity<Object> unzipAndSendFiles(@PathVariable Integer userId, @PathVariable Integer fileId) {
        try {
            Map<String, byte[]> unzippedFiles = fileModelService.unzipFiles(userId, fileId);
            return ResponseEntity.ok().body(unzippedFiles);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}