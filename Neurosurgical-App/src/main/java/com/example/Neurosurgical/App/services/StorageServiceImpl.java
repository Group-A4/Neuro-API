package com.example.Neurosurgical.App.services;

import com.azure.core.util.BinaryData;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobRequestConditions;
import com.azure.storage.blob.models.PublicAccessType;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;


import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class StorageServiceImpl implements StorageService{
    private static final Tika tika = new Tika();
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
        blobServiceClient.createBlobContainer(containerName).setAccessPolicy(PublicAccessType.CONTAINER, null);
    }

    public boolean verifyIfContainerExists(String containerName) {
        return blobServiceClient.getBlobContainerClient(containerName).exists();
    }

    @Override
    public void uploadFile(String containerName, String blobName, byte[] fileBytes) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        
        String type = tika.detect(fileBytes);

        BlobHttpHeaders jsonHeaders = new BlobHttpHeaders()
                .setContentType(type);

        BinaryData data = BinaryData.fromByteBuffer(java.nio.ByteBuffer.wrap(fileBytes));
        BlobParallelUploadOptions options = new BlobParallelUploadOptions(data)
                .setRequestConditions(new BlobRequestConditions()).setHeaders(jsonHeaders);
        blobClient.uploadWithResponse(options, null, Context.NONE);
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
