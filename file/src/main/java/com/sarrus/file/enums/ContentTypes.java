package com.sarrus.file.enums;

public enum ContentTypes {
    MP4("video/mp4"),
    HTML("text/html"),
    JPEG("image/jpeg"),
    JPG(JPEG.getValue()),
    PNG("image/png");

    private String value;

    ContentTypes(String v) {
        this.value = v;
    }

    public static String getByValue(String value) {
        switch (value) {
            case "mp4":
                return "video/mp4";
            case "html":
                return "text/html";
            case "jpeg", "jpg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return "";
        }
    }

    public String getValue() {
        return value;
    }
}
