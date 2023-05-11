package com.example.Neurosurgical.App.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface StorageService {
    void createContainer(String containerName);
    boolean verifyIfContainerExists(String containerName);
    void uploadFile(String containerName, String blobName, MultipartFile file) throws IOException;
    byte[] downloadFile(String containerName, String blobName) throws IOException;
    void deleteFile(String containerName, String blobName);
}
