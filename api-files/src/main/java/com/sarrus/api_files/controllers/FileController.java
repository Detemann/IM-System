package com.sarrus.api_files.controllers;

import com.sarrus.api_files.dto.FileDTO;
import com.sarrus.api_files.service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping
    public ResponseEntity<Object> uploadFile(@Valid FileDTO fileDTO) throws IOException {
        fileService.send(fileDTO);
        return ResponseEntity.ok().body("Arquivo Enviado com sucesso!");
    }
}
