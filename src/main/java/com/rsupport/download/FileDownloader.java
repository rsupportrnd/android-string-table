package com.rsupport.download;

import com.rsupport.download.google.drive.GoogleDriveDownload;

import java.io.File;

public class FileDownloader {

    public static File download(String credentialFilePath, String fileId, String fileName) {
        return new GoogleDriveDownload(credentialFilePath, fileId, fileName).execute();
    }
}
