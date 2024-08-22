package com.sarrus.api_files.misc;

public class ByteArrayResource extends org.springframework.core.io.ByteArrayResource {

    private String fileName;

    public ByteArrayResource(byte[] byteArray, String filename) {
        super(byteArray);
        this.fileName = filename;
    }

    @Override
    public String getFilename() {
        return this.fileName;
    }
}
