package com.example.Neurosurgical.App.services;


import com.example.Neurosurgical.App.advice.exceptions.ContentNotFound;
import com.example.Neurosurgical.App.models.entities.ContentEntity;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownParserServiceImpl implements MarkdownParserService{
    private static final String AZURE_STORAGE_URL = "https://%s.blob.core.windows.net/%s/%s";
    private static final String imagePattern = "!image:([\\w\\.]+)";
    private static final String videoPattern = "!video:([\\w\\.]+)";
    private static final String pdfPattern = "!pdf:([\\w\\.]+)";
//    private static final String pptPattern = "!ppt:([\\w\\.]+)";
    private static final String audioPattern = "!audio:([\\w\\.]+)";
    private static final String videoFormatString = "\n <video width=\"320\" height=\"240\" controls>\n" +
            "   <source src=\"%s\" type=\"video/mp4\">\n" +
            "   Your browser does not support the video tag.\n" +
            " </video>";
    private static final String pdfFormatString = "\n <iframe src=\"%s\" width=\"100%%\" height=\"600px\">\n" +
            "   <p>Your browser does not support iframes.</p>\n" +
            " </iframe>";

    private static final String audioFormatString = "\n <audio controls>\n" +
            "   <source src=\"%s\" type=\"audio/mpeg\">\n" +
            "   Your browser does not support the audio element.\n" +
            " </audio>";


    private final String azureAccountName;
    private final String azureContainerName;
    private final List<ContentEntity> contentEntities;

    public MarkdownParserServiceImpl(String azureAccountName, String azureContainerName, List<ContentEntity> contentEntities) {
        this.azureAccountName = azureAccountName;
        this.azureContainerName = azureContainerName;
        this.contentEntities = contentEntities;
    }

    @Override
    public String parse(String markdownText) {
        Pattern pattern = Pattern.compile(imagePattern);
        Matcher matcher = pattern.matcher(markdownText);
        while (matcher.find()) {
            String imageName = matcher.group(1);
            if(!exists(imageName))
                throw new ContentNotFound(imageName);
            String imageUrl = String.format(AZURE_STORAGE_URL, azureAccountName, azureContainerName, imageName);
            markdownText = markdownText.replace("!image:" + imageName, imageUrl);
        }

        pattern = Pattern.compile(videoPattern);
        matcher = pattern.matcher(markdownText);
        while (matcher.find()) {
            String videoName = matcher.group(1);
            if(!exists(videoName))
                throw new ContentNotFound(videoName);
            String videoUrl = String.format(AZURE_STORAGE_URL, azureAccountName, azureContainerName, videoName);
            String videoTag = String.format(videoFormatString, videoUrl);
            markdownText = markdownText.replace("!video:" + videoName, videoTag);
        }

        pattern = Pattern.compile(pdfPattern);
        matcher = pattern.matcher(markdownText);
        while (matcher.find()) {
            String pdfName = matcher.group(1);
            if(!exists(pdfName))
                throw new ContentNotFound(pdfName);
            String pdfUrl = String.format(AZURE_STORAGE_URL, azureAccountName, azureContainerName, pdfName);
            String pdfTag = String.format(pdfFormatString, pdfUrl);
            markdownText = markdownText.replace("!pdf:" + pdfName, pdfTag);
        }

//        pattern = Pattern.compile(pptPattern);
//        matcher = pattern.matcher(markdownText);
//        while (matcher.find()) {
//            String pptName = matcher.group(1);
//            if(!exists(pptName))
//                throw new ContentNotFound(pptName);
//            String pptUrl = String.format(AZURE_STORAGE_URL, azureAccountName, azureContainerName, pptName);
//            String pptTag = String.format(pdfFormatString, pptUrl);
//            markdownText = markdownText.replace("!ppt:" + pptName, pptTag);
//        }

        pattern = Pattern.compile(audioPattern);
        matcher = pattern.matcher(markdownText);
        while (matcher.find()) {
            String audioName = matcher.group(1);
            if(!exists(audioName))
                throw new ContentNotFound(audioName);
            String audioUrl = String.format(AZURE_STORAGE_URL, azureAccountName, azureContainerName, audioName);
            String audioTag = String.format(audioFormatString, audioUrl);
            markdownText = markdownText.replace("!audio:" + audioName, audioTag);
        }

        return markdownText;
    }

    private boolean exists(String name) {
        for (ContentEntity contentEntity : contentEntities) {
            if (contentEntity.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
