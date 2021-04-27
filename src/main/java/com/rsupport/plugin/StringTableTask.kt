package com.rsupport.plugin

import com.rsupport.GoogleCredentials
import com.rsupport.ParsingSheetURL
import com.rsupport.download.FileDownloader
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class StringTableTask : DefaultTask() {

    var googleDriveCredentialPath = ""
    var targetSheetUrl = ""
    var outputXlsxFilePath = ""
    var androidResourcePath = ""
    var indexRowNumber = 0

    private val credential = GoogleCredentials.createCredentials(googleDriveCredentialPath)

    @TaskAction
    @kotlin.jvm.Throws(Exception::class)
    fun updateStringResource() {
        val sheetURLParser = ParsingSheetURL(credential, targetSheetUrl)
        val source: File? = FileDownloader.download(credential, sheetURLParser.spreadSheetId, outputXlsxFilePath)
        if (source != null) {
            StringTableGenerator.generate(outputXlsxFilePath, androidResourcePath, sheetURLParser.sheetName, indexRowNumber)
        }
    }
}