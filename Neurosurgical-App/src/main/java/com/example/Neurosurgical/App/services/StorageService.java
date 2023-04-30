package com.example.Neurosurgical.App.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface StorageService {
    void uploadImage(String containerName, String blobName, byte[] imageBytes) throws IOException;
    byte[] downloadImage(String containerName, String blobName) throws IOException;
}
