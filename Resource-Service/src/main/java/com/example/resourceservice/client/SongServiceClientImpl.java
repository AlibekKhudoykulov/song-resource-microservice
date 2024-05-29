package com.example.resourceservice.client;

import com.example.resourceservice.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class SongServiceClientImpl implements SongServiceClient {
    private final WebClient webClient;


    @Override
    public void saveMetadata(SongDTO songDTO) {
        webClient.post()
                .uri("http://song-service:8081/songs")
                .bodyValue(songDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().toBodilessEntity().block();
    }

    @Override
    public void deleteMetadataByResourceId(Integer id) {
        webClient.delete()
                .uri("http://song-service:8081/songs?id=" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().toBodilessEntity().block();
    }
}
