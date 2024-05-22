package com.example.songservice.service;

import com.example.songservice.dto.SongDTO;
import com.example.songservice.entity.Song;
import com.example.songservice.repository.SongRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SongServiceImpl implements SongService{
    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public ResponseEntity<Integer> createMetadata(SongDTO songDTO) {
        Song song = new Song(
                songDTO.getName(),
                songDTO.getArtist(),
                songDTO.getAlbum(),
                songDTO.getLength(),
                songDTO.getResourceId(),
                songDTO.getYear()
        );
        Song save = songRepository.save(song);
        Map<String, Integer> response = new HashMap<>();
        response.put("id", save.getId());
        return new ResponseEntity<>(save.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSongByID(Integer songId) {
        try {
            Optional<Song> optionalSong = songRepository.findById(songId);
            if(optionalSong.isPresent()){
                Song song = optionalSong.get();
                SongDTO songDTO = new SongDTO(
                        song.getName(),
                        song.getArtist(),
                        song.getAlbum(),
                        song.getLength(),
                        song.getResourceId(),
                        song.getYear()
                );
                return new ResponseEntity<>(songDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The song metadata with the specified id does not exist", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("An internal server error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteSongByIDs(String id) {
        try {
            List<Integer> ids =
                    Arrays.stream(id.split(","))
                            .map(Integer::parseInt)
                            .toList();

            for (Integer songId : ids) {
                songRepository.deleteById(songId);
            }

            Map<String, List<Integer>> response = new HashMap<>();
            response.put("ids", ids);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("An internal server error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }    }
}
