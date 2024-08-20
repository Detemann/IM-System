package com.sarrus.api_files.service;

import com.sarrus.api_files.dto.FileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FileService {

    private final WebClient webClient;

    public FileService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Todo
     * Fazer request para o servidor de arquivos
     * **/
    public void sendFileToServer(FileDTO fileDTO) {

    }
}
