package com.example.resourceservice.client;

import com.example.resourceservice.dto.SongDTO;

public interface SongServiceClient {
    void saveMetadata(SongDTO songDTO);

    void deleteMetadataByResourceId(Integer id);
}
