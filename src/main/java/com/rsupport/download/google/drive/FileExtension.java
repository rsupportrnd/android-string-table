package com.rsupport.download.google.drive;

import com.google.api.services.drive.model.File;

import java.util.HashMap;
import java.util.Map;

interface FileExtension {
    String extension();

    String exportMimeType();

    static FileExtension from(File file) {
        return new DriveExtension(file);
    }
}

class DriveExtension implements FileExtension {

    private final File file;

    DriveExtension(File file) {
        this.file = file;
    }

    private Map<String, String> extensionMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("application/vnd.google-apps.spreadsheet", "xlsx");
        return map;
    }

    private Map<String, String> outMimeTypeMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("application/vnd.google-apps.spreadsheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return map;
    }

    @Override
    public String extension() {
        return extensionMap().get(file.getMimeType());
    }

    @Override
    public String exportMimeType() {
        return outMimeTypeMap().get(file.getMimeType());
    }
}
