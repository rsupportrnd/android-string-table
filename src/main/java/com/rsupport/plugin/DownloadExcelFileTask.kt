package com.rsupport.plugin

import com.rsupport.download.FileDownloader
import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

open class DownloadExcelFileTask : DefaultTask() {

    @Internal var googleDriveCredentialPath = ""
    @Internal var targetSheetUrl = ""
    @Internal var outputXlsxFilePath = ""

    @TaskAction
    fun downloadExcelFile() {
        val credential = GoogleCredentials.createCredentials(googleDriveCredentialPath)
        val sheetURLParser = SheetUrlParser(credential, targetSheetUrl)
        FileDownloader.download(credential, sheetURLParser.spreadSheetId, outputXlsxFilePath)
    }
}