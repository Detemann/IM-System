package com.sarrus.api_files.service;

import com.sarrus.api_files.dto.RequestFileDTO;
import com.sarrus.api_files.dto.RequestFileUpdateDTO;
import com.sarrus.api_files.dto.ResponseFileDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    private final WebClient webClient;

    public FileService(WebClient webClient) {
        this.webClient = webClient;
    }
/*TODO
*
* Mudar uri quando o controller especifico de file por criado
* */
    public ResponseEntity<String> send(RequestFileDTO requestFileDTO) {

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("user", requestFileDTO.user());
        bodyMap.add("playlist", requestFileDTO.playlist());
        bodyMap.add("time", requestFileDTO.time());
        Arrays.stream(requestFileDTO.files()).forEach(file -> {
            try {
                bodyMap.add("file", new ByteArrayResource(file.getBytes()));
            } catch (IOException e) {
                System.out.println("[FileService -> send] "+e.getMessage());
                throw new RuntimeException(e);
            }
        });

        return webClient.post()
            .uri("/file")
            .body(BodyInserters.fromMultipartData(bodyMap))
            .retrieve()
            .toEntity(String.class)
            .block();
    }

    public List<ResponseFileDTO> getAllFiles(Integer userId) {
        return webClient.get()
                .uri("/files/{userId}", userId)
                .retrieve()
                .bodyToFlux(ResponseFileDTO.class)
                .collectList()
                .block();
    }

    public List<ResponseFileDTO> getFilesByPlaylistId(RequestFileDTO requestFileDTO) {
        return webClient.post()
                .uri("/file/download")
                .body(BodyInserters.fromValue(requestFileDTO))
                .retrieve()
                .bodyToFlux(ResponseFileDTO.class)
                .collectList()
                .block();
    }

    public ResponseEntity<String> deleteFile(Integer fileId) {
        return webClient.delete()
                .uri("/files/{fileId}", fileId)
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    public ResponseEntity<String> updateFile(RequestFileUpdateDTO requestFileDTO) {
        return webClient.put()
                .uri("/file")
                .body(BodyInserters.fromValue(requestFileDTO))
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
