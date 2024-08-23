package com.sarrus.file.controllers;

import com.sarrus.file.dtos.FileDTO;
import com.sarrus.file.services.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<Object> test(@Valid FileDTO fileDTO) throws IOException {
        fileService.save(fileDTO);
        return ResponseEntity.ok().body("Arquivo recebido com sucesso");
    }
}
