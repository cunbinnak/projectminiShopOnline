package com.example.shopingcart.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {

    public void init();

    public void saveFile(MultipartFile file);
    public Resource load(String fileName);
    public Stream<Path> loadAll();
}
