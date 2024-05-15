package com.example.songservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {
    private String name;
    private String artist;
    private String album;
    private String length;
    private int resourceId;
    private int year;
}