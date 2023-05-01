package com.example.Neurosurgical.App.models.entities;

public enum ContentType {
    VIDEO,
    TEXT,
    IMAGE,
    AUDIO,
    PDF,
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
