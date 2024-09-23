package com.sarrus.api_files.controllers;

import com.sarrus.api_files.dto.RequestFileDTO;
import com.sarrus.api_files.dto.ResponseFileDTO;
import com.sarrus.api_files.service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping
    public ResponseEntity<Object> uploadFile(@Valid RequestFileDTO requestFileDTO) throws IOException {
        return fileService.send(requestFileDTO);
    }

    /*
    * Todo
    *  Fazer endpoint GET que faça a requisição do endpoint do file GET que pegue todos os arquivos vinculados a uma playlist -
    *  esse deve passar o id do usuário e id da playlist
    * */
    //DO HERE

    @GetMapping
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

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") Integer fileId) {
        boolean isDeleted = fileService.deleteFile(fileId);
        if (isDeleted) {
            return ResponseEntity.ok().body("Arquivo deletado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
