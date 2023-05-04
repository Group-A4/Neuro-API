package com.example.Neurosurgical.App.config;

import com.example.Neurosurgical.App.services.StorageService;
import com.example.Neurosurgical.App.services.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageServiceConfig {

//    @Value("${azure.storage.account-name}")
    private String accountName;

//    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.blob-endpoint}")
    private String blobEndpoint;

    @Bean
    public StorageService storageService() {
        String accountName = System.getenv("AZURE_STORAGE_ACCOUNT");
        String accountKey = System.getenv("AZURE_STORAGE_ACCESS_KEY");
        return new StorageServiceImpl(accountName, accountKey, blobEndpoint);
    }
}
