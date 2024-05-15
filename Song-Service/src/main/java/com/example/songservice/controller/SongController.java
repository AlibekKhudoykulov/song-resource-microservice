package com.example.songservice.controller;

import com.example.songservice.dto.SongDTO;
import com.example.songservice.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {
    private final SongService service;

    @PostMapping
    public ResponseEntity<Integer> createSongMetadata(@RequestBody SongDTO songDTO){
        return service.createMetadata(songDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSongMetadataById(@PathVariable Integer id){
        return service.getSongByID(id);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteSongs(@RequestParam String id) {
       return service.deleteSongByIDs(id);
    }

}
