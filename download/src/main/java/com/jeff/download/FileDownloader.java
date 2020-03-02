package com.jeff.download;

import com.jeff.download.google.drive.GoogleDriveDownload;

public class FileDownloader {

    public static void main(String[] args) {
        if (args == null || args.length < 2) throw new RuntimeException("not found argument.");
        String credentialsFile = args[0];
        String fileId = args[1];
        GoogleDriveDownload downloadable = new GoogleDriveDownload(credentialsFile, fileId);
        downloadable.execute();
    }
}
