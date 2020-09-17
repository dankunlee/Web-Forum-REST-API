package com.dankunlee.forumapp.controller;

import com.dankunlee.forumapp.dto.FileDTO;
import com.dankunlee.forumapp.entity.File;
import com.dankunlee.forumapp.entity.Post;
import com.dankunlee.forumapp.repository.FileRepository;
import com.dankunlee.forumapp.repository.PostRepository;
import com.dankunlee.forumapp.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/api/post/{id}/upload-file")
    public FileDTO uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String fileName = fileService.storeFile(id, file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/api/post/")
                                    .path(Long.toString(id))
                                    .path("/download-file/")
                                    .path(fileName)
                                    .toUriString();
        File uploadFile = new File();
        uploadFile.setFileName(fileName);
        uploadFile.setFileType(file.getContentType());
        uploadFile.setFileUri(fileDownloadUri);
        uploadFile.setFileSize(file.getSize());

        Post post = postRepository.findById(id).get();
        uploadFile.setPost(post);

        fileRepository.save(uploadFile);

        return new FileDTO(fileName, file.getContentType(), fileDownloadUri, file.getSize());
    }

    @PostMapping("/api/post/{id}/upload-multiple-files")
    public List<FileDTO> uploadMultipleFiles(@PathVariable Long id, @RequestParam("files") MultipartFile[] files) {
        List<FileDTO> list = new ArrayList<>();
        for (MultipartFile file : files)
            list.add(uploadFile(id, file));
        return list;
    }

    @GetMapping("/api/post/{id}/files")
    public List<FileDTO> downloadFilesInfo(@PathVariable Long id) {
        List<File> files = fileRepository.findAllByPostId(id);
        List<FileDTO> fileDTOs = new ArrayList<>();
        for (File file : files)
            fileDTOs.add(new FileDTO(file.getFileName(), file.getFileType(), file.getFileUri(), file.getFileSize()));
        return fileDTOs;
    }

    @GetMapping("/api/post/{id}/download-file/{fileName:.+}") // regex .+ for file extension
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, @PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.loadFile(id, fileName);

        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.info("Unknown File Type", e);
        }

        if (contentType == null) contentType = "application/octect-stream";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                                  .header(HttpHeaders.CONTENT_DISPOSITION, "attatchment; filename=\""
                                                                            + resource.getFilename() + "\"")
                                  .body(resource);
    }

}
