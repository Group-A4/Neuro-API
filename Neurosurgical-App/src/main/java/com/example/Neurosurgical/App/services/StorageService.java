package com.example.Neurosurgical.App.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface StorageService {
    void uploadFile(String containerName, String blobName, byte[] imageBytes) throws IOException;
    byte[] downloadFile(String containerName, String blobName) throws IOException;
}
