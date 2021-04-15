package com.rsupport.download

import com.rsupport.download.google.drive.GoogleDriveDownload
import java.io.File

object FileDownloader {
    fun download(googleDriveCredentialPath: String, spreadSheetFieldId: String, outputExcelFileName: String, outputExcelFilePath: String): File? {
        val downloadable = GoogleDriveDownload(googleDriveCredentialPath, spreadSheetFieldId, outputExcelFileName, outputExcelFilePath)
        return downloadable.execute()
    }
}