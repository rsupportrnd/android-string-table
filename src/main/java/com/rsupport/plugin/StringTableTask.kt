package com.rsupport.plugin

import com.rsupport.download.FileDownloader
import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class StringTableTask : DefaultTask() {

    var googleDriveCredentialPath = ""
    var targetSheetUrl = ""
    var outputXlsxFilePath = ""
    var androidResourcePath = ""
    var rowPositionColumnHeader : Int? = null
    var outputXmlFileName : String? = null
    @TaskAction
    @kotlin.jvm.Throws(Exception::class)
    fun updateStringResource() {
        val credential = GoogleCredentials.createCredentials(googleDriveCredentialPath)
        val sheetURLParser = SheetUrlParser(credential, targetSheetUrl)
        FileDownloader.download(credential, sheetURLParser.spreadSheetId, outputXlsxFilePath)
        StringTableGenerator.generate(
            outputXlsxFilePath,
            androidResourcePath,
            sheetURLParser.sheetName,
            rowPositionColumnHeader,
            outputXmlFileName
        )
    }
}