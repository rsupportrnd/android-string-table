package com.jeff.download;

import com.jeff.download.google.drive.GoogleDriveDownload;

public class FileDownloader {

    public static void main(String[] args) {
        if (args == null || args.length < 1) throw new RuntimeException("not found argument.");
        String fileId = args[0];
        GoogleDriveDownload downloadable = new GoogleDriveDownload(fileId);
        downloadable.execute();
    }
}
