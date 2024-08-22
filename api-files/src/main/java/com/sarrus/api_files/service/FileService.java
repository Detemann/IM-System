package com.sarrus.api_files.service;

import com.sarrus.api_files.dto.FileDTO;
import com.sarrus.api_files.misc.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
    public String send(FileDTO fileDTO) {
        try {
            Resource file = new ByteArrayResource(fileDTO.file().getBytes(), fileDTO.file().getOriginalFilename());

            MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
            bodyMap.add("user", fileDTO.user());
            bodyMap.add("playlist", fileDTO.playlist());
            bodyMap.add("time", fileDTO.time());
            bodyMap.add("file", file);

            return webClient.post()
                    .uri("/test")
                    .bodyValue(bodyMap)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException | IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
