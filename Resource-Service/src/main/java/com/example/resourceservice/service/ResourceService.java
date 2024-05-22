package com.example.resourceservice.service;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ResourceService {
    Integer createResource(byte[] data) throws TikaException, IOException, SAXException;

    byte[] getResourceById(Integer id);

    List<Integer> deleteByIds(String idsString);
}
