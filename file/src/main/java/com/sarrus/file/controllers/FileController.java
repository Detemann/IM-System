package com.sarrus.file.controllers;

import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.services.file.FileModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileModelService fileModelService;

    @PostMapping
    public ResponseEntity<Object> saveFiles(@Valid FileDTO fileDTO) throws IOException {
        fileModelService.saveAndStore(fileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> sendPlaylistFiles(@Valid FileDTO fileDTO){
        MultiValueMap<String, HttpEntity<?>> unzippedFiles = fileModelService.unzipFiles(fileDTO.user(), fileDTO.playlist());
        return new ResponseEntity<>(unzippedFiles, HttpStatus.OK);
    }
}