package com.example.songservice.service;

import com.example.songservice.dto.SongDTO;
import org.springframework.http.ResponseEntity;

public interface SongService {
    ResponseEntity<Integer> createMetadata(SongDTO songDTO);
    ResponseEntity<?> getSongByID(Integer id);
    ResponseEntity<?> deleteSongByIDs(String ids);

}
