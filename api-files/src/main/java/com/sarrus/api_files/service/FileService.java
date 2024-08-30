package com.sarrus.api_files.service;

import com.sarrus.api_files.dto.FileDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;

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
    public ResponseEntity send(FileDTO fileDTO) throws IOException {

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("user", fileDTO.user());
        bodyMap.add("playlist", fileDTO.playlist());
        bodyMap.add("time", fileDTO.time());
        Arrays.stream(fileDTO.files()).forEach(file -> {
            try {
                bodyMap.add("file", new ByteArrayResource(file.getBytes()));
            } catch (IOException e) {
                System.out.println("[FileService -> send] "+e.getMessage());
                throw new RuntimeException(e);
            }
        });

        return webClient.post()
                .uri("/test")
                .body(BodyInserters.fromMultipartData(bodyMap))
                .retrieve()
                .toEntity(ResponseEntity.class)
                .block();
    }
}
