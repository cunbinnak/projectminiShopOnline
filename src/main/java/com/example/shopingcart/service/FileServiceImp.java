package com.example.shopingcart.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Service
public class FileServiceImp implements FileService{


    // thư mục chứa ảnh
    private final Path root = Paths.get("src/main/resources/images");
    @Override
    public void init() {
        try {

            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Không thể up ảnh lên!");
        }
    }

    @Override
    public void saveFile(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("không thể lưu trữ ảnh" + e.getMessage());
        }
    }

    @Override
    public Resource load(String fileName) {


        try {
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new RuntimeException("không thể đọc đc file");
            }
        } catch (MalformedURLException e) {
            throw  new RuntimeException("Error" + e.getMessage());
        }
    }

//    @Override
//    public void deleteAll() {
//
//        FileSystemUtils.deleteRecursively(root.toFile());
//    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root,1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        }catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
