package com.example.resourceservice.controller;

import com.example.resourceservice.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(consumes = "audio/mpeg")
    public ResponseEntity<Map<String, Integer>> upload(@RequestBody byte[] data) throws IOException, TikaException, SAXException {
        Integer id = resourceService.createResource(data);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping(value = "/{id}") public ResponseEntity<byte[]> get(@PathVariable Integer id) {
        byte[] data = resourceService.getResourceById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf("audio/mpeg")).body(data);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, List<Integer>>> deleteByIds(@RequestParam String ids) {
        var deletedIds = resourceService.deleteByIds(ids);
        return ResponseEntity.ok().body(Map.of("ids", deletedIds));
    }
}