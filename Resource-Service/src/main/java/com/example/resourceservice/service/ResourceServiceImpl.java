package com.example.resourceservice.service;

import com.example.resourceservice.client.SongServiceClient;
import com.example.resourceservice.dto.SongDTO;
import com.example.resourceservice.entity.Resource;
import com.example.resourceservice.exception.ResourceNotFoundException;
import com.example.resourceservice.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService{
    private final ResourceRepository resourceRepository;
    private final SongServiceClient songServiceClient;
    public ResourceServiceImpl(ResourceRepository resourceRepository, SongServiceClient songServiceClient) {
        this.resourceRepository = resourceRepository;
        this.songServiceClient = songServiceClient;
    }

    @Override
    public Integer createResource(InputStream data) throws TikaException, IOException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        byte[] byteArray = data.readAllBytes();

        Mp3Parser Mp3Parser = new Mp3Parser();
        Mp3Parser.parse(new ByteArrayInputStream(byteArray), handler, metadata, context);

        Resource resource = new Resource();
        resource.setContent(byteArray);
        Resource save = resourceRepository.save(resource);
        Integer id = save.getId();
        SongDTO songInfoDto = SongDTO.builder()
                .name(parseName(metadata))
                .artist(parseArtist(metadata))
                .album(parseAlbum(metadata))
                .length(parseLength(metadata))
                .year(parseYear(metadata))
                .resourceId(id)
                .build();
        songServiceClient.saveMetadata(songInfoDto);
        return id;
    }

    @Override
    public byte[] getResourceById(Integer id) {
        return resourceRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new)
                .getContent();
    }

    @Override
    public List<Integer> deleteByIds(String idsString) {
        ArrayList<Integer> idsList = new ArrayList<Integer>();
        for (String s : idsString.split(",")) {
            try {
                idsList.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {
            }
        }

        ArrayList<Integer> deletedIds = new ArrayList<Integer>();
        idsList.forEach(id -> {
            if (resourceRepository.existsById(id)) {
                resourceRepository.deleteById(id);
                songServiceClient.deleteMetadataByResourceId(id);
                deletedIds.add(id);
            }
        });

        return deletedIds;
    }

    private String parseYear(Metadata metadata) {
        return metadata.get("xmpDM:releaseDate");
    }

    private String parseName(Metadata metadata) {
        return metadata.get("dc:title");
    }

    private String parseAlbum(Metadata metadata) {
        return metadata.get("xmpDM:album");
    }

    private String parseArtist(Metadata metadata) {
        return metadata.get("xmpDM:albumArtist");
    }

    private String parseLength(Metadata metadata) {
        String durationString = metadata.get("xmpDM:duration");
        double milliseconds = Double.parseDouble(durationString);
        int seconds = (int) (milliseconds / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
