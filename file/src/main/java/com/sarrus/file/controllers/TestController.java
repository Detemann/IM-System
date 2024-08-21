package com.sarrus.file.controllers;

import com.sarrus.file.dtos.FileDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    public ResponseEntity<Object> test(@Valid FileDTO fileDTO) {
        System.out.println(fileDTO);
        return ResponseEntity.ok().body("Arquivo recebido com sucesso");
    }
}
