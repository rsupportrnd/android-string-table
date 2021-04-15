package com.rsupport.download

import com.rsupport.download.google.drive.GoogleDriveDownload
import java.io.File

object FileDownloader {
    fun download(googleDriveCredentialPath: String, spreadSheetFieldId: String, outputXlsxFilePath: String): File? {
        val downloadable = GoogleDriveDownload(googleDriveCredentialPath, spreadSheetFieldId, outputXlsxFilePath)
        return downloadable.execute()
    }
}