package com.rsupport.download;

import com.rsupport.download.google.drive.GoogleDriveDownload;

import java.io.File;

public interface Downloadable {
    File execute();

    static void from(String credentialFile, String fileId) {
        from(credentialFile, fileId, null);
    }

    static Downloadable from(String credentialFile, String fileId, String fileName) {
        return new GoogleDriveDownload(credentialFile, fileId, fileName);
    }
}
