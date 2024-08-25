package com.sarrus.api_files.service;

import com.sarrus.api_files.dto.FileDTO;
import com.sarrus.api_files.exceptions.RetrieveException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

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
        for (MultipartFile file : fileDTO.files()) {
            ByteArrayResource content = new ByteArrayResource(file.getBytes()) {

                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            bodyMap.add("files", content);
        }

        return webClient.post()
                .uri("/test")
                .body(BodyInserters.fromMultipartData(bodyMap))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RetrieveException(clientResponse.statusCode(), "Upload to file server returned error")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RetrieveException(clientResponse.statusCode(), "Upload to file server returned error")))
                .toEntity(ResponseEntity.class)
                .block();
    }
}
