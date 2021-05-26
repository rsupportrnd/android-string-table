package com.rsupport.download

import com.google.api.client.auth.oauth2.Credential
import com.rsupport.google.drive.GoogleDriveDownload
import java.io.File

object FileDownloader {
    fun download(credential: Credential?, spreadSheetFieldId: String?, outputXlsxFilePath: String): File? {
        if(credential == null) throw NullPointerException("Credential is null.")
        if(spreadSheetFieldId.isNullOrEmpty()) throw NullPointerException("Spread Sheet Id is null.")
        val downloadable = GoogleDriveDownload(credential, spreadSheetFieldId, outputXlsxFilePath)
        return downloadable.execute()
    }
}