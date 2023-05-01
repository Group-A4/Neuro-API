package com.example.Neurosurgical.App.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StorageServiceImpl implements StorageService{
    private final BlobServiceClient blobServiceClient;

    public StorageServiceImpl(@Value("${azure.storage.account-name}") String accountName,
                              @Value("${azure.storage.account-key}") String accountKey,
                              @Value("${azure.storage.blob-endpoint}") String blobEndpoint) {
        String connectionString = "DefaultEndpointsProtocol=https;"
                + "AccountName=" + accountName
                + ";AccountKey=" + accountKey
                + ";EndpointSuffix=core.windows.net";
        blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }

    public void createContainer(String containerName) {
        blobServiceClient.createBlobContainer(containerName);
    }

    public boolean verifyIfContainerExists(String containerName) {
        return blobServiceClient.getBlobContainerClient(containerName).exists();
    }

    public void uploadFile(String containerName, String blobName, byte[] imageBytes) throws IOException {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        try (InputStream dataStream = new ByteArrayInputStream(imageBytes)) {
            blobClient.upload(dataStream, imageBytes.length);
        }

    }

    public byte[] downloadFile(String containerName, String blobName) throws IOException {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.download(outputStream);
        return outputStream.toByteArray();
    }

    public void deleteFile(String containerName, String blobName) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.delete();
    }

}
