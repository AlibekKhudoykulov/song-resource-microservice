package com.example.resourceservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongDTO {
    private String name;
    private String artist;
    private String album;
    private String length;
    private int resourceId;
    private String year;
}
