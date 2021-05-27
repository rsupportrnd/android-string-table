package com.rsupport.plugin

import com.rsupport.download.FileDownloader
import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class DownloadExcelFileTask : DefaultTask() {

    var googleDriveCredentialPath = ""
    var targetSheetUrl = ""
    var outputXlsxFilePath = ""

    @TaskAction
    fun downloadExcelFile() {
        val credential = GoogleCredentials.createCredentials(googleDriveCredentialPath)
        val sheetURLParser = SheetUrlParser(credential, targetSheetUrl)
        FileDownloader.download(credential, sheetURLParser.spreadSheetId, outputXlsxFilePath)
    }
}