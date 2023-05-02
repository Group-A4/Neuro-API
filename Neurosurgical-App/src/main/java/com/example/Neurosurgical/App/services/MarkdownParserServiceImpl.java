package com.example.Neurosurgical.App.services;


import com.example.Neurosurgical.App.advice.exceptions.ContentNotFound;
import com.example.Neurosurgical.App.models.entities.ContentEntity;
import com.ibm.icu.impl.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownParserServiceImpl implements MarkdownParserService{
    private static final String AZURE_STORAGE_URL = "https://%s.blob.core.windows.net/%s/%s";

    private static final String imageFormatString = "\n <img src=\"%s\" alt=\"%s\" />";
    private static final String videoFormatString = "\n <video width=\"320\" height=\"240\" controls>\n" +
            "   <source src=\"%s\" type=\"video/mp4\">\n" +
            "   Your browser does not support the video tag.\n" +
            " </video>";
    private static final String pdfFormatString = "\n <iframe src=\"%s\" width=\"100%%\" height=\"600px\">\n" +
            "   <p>Your browser does not support iframes.</p>\n" +
            " </iframe>";
    private static final String pptFormatString = "\n <iframe src=\"https://view.officeapps.live.com/op/embed.aspx?src=%s\" frameborder=\"0\" style=\"width: 100%%; height: 600px;\">\n" +
            "   <p>Your browser does not support iframes.</p>\n" +
            " </iframe>";
    private static final String audioFormatString = "\n <audio controls>\n" +
            "   <source src=\"%s\" type=\"audio/mpeg\">\n" +
            "   Your browser does not support the audio element.\n" +
            " </audio>";
    private static final String linkFormatString = "\n <a href=\"%s\">%s</a>";
    private static final String ytVideoFormatString = "\n <iframe width=\"1026\" height=\"577\" src=\"https://www.youtube.com/embed/%s\" title=\"%s\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";


    private final String azureAccountName;
    private final String azureContainerName;
    private final List<ContentEntity> contentEntities;
    private final Map<String, Pair<String, Function<List<String>, String>>> contentMap;

    public MarkdownParserServiceImpl(String azureAccountName, String azureContainerName, List<ContentEntity> contentEntities) {
        this.azureAccountName = azureAccountName;
        this.azureContainerName = azureContainerName;
        this.contentEntities = contentEntities;

        this.contentMap = new HashMap<>();
        contentMap.put("!image", Pair.of(imageFormatString, this::parseThree));
        contentMap.put("!video", Pair.of(videoFormatString, this::parseTwo));
        contentMap.put("!pdf", Pair.of(pdfFormatString, this::parseTwo));
        contentMap.put("!ppt", Pair.of(pptFormatString, this::parseTwo));
        contentMap.put("!audio", Pair.of(audioFormatString, this::parseTwo));
        contentMap.put("!link", Pair.of(linkFormatString, this::parseThree));
        contentMap.put("!ytvideo", Pair.of(ytVideoFormatString, this::parseThree));
    }

    @Override
    public String parse(String markdownText) {
        markdownText = markdownText.replaceAll("https://www.youtube.com/watch\\?v=", "");

        var tags = contentMap.keySet();
        for (String tag : tags) {
            Pattern pattern = Pattern.compile(tag + ":([\\w\\.]+)");
            Matcher matcher = pattern.matcher(markdownText);
            while (matcher.find()) {
                String fileName = matcher.group(1);
                if(!exists(fileName) && !tag.equals("!ytvideo"))
                    throw new ContentNotFound(fileName);
                String fileUrl = String.format(AZURE_STORAGE_URL, azureAccountName, azureContainerName, fileName);
                if(tag.equals("!ytvideo"))
                    fileUrl = fileName;
                String fileTag = contentMap.get(tag).second.apply(List.of(contentMap.get(tag).first, fileUrl, fileName));
                markdownText = markdownText.replace(tag + ":" + fileName, fileTag);
            }
        }

        return markdownText;
    }

    private String parseTwo(List<String> args){
        return String.format(args.get(0), args.get(1));
    }

    private String parseThree(List<String> args){
        return String.format(args.get(0), args.get(1), args.get(2));
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
