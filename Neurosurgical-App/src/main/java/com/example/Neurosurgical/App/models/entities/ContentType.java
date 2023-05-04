package com.example.Neurosurgical.App.models.entities;

public enum ContentType {
    VIDEO,
    IMAGE,
    AUDIO,
    PDF,
    PPT,
    LINKS,
    YT_VIDEO,
    OTHER;

    public static ContentType fromString(String text) {
        for (ContentType b : ContentType.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
