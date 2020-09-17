package com.dankunlee.forumapp.service;

import com.dankunlee.forumapp.config.FileConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    public final Path fileStoragePath;
    private final String POST = "post_";

    @Autowired
    public FileService(FileConfigurer fileConfigurer) {
        fileStoragePath = Paths.get(fileConfigurer.getPath()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new FileStorageException("Cannot create directories for uploaded files");
        }
    }

    public String storeFile(Long id, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            Path fileNamePath = fileStoragePath.resolve(POST + id).resolve(fileName);
            Files.createDirectories(fileNamePath);
            Files.copy(file.getInputStream(), fileNamePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Unable to upload " + fileName, e);
        }
    }

    public Resource loadFile(Long id, String fileName) {
        try {
            Path fileNamePath = fileStoragePath.resolve(POST + id).resolve(fileName);
            Resource resource = new UrlResource(fileNamePath.toUri());

            if (resource.exists()) return resource;
            else throw new FileNotFoundException(fileName + " is not found");
        } catch (MalformedURLException e) {
            throw new FileNotFoundException(fileName + " is not found", e);
        }
    }
}

// handles failure of file uploading
class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

// handles failure of file downloading
@ResponseStatus(HttpStatus.NOT_FOUND)
class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
