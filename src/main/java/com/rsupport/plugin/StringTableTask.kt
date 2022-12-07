package com.rsupport.plugin

import com.rsupport.download.FileDownloader
import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File

open class StringTableTask : DefaultTask() {

    @Internal var googleDriveCredentialPath = ""
    var targetSheetUrl = ""
    @Internal var outputXlsxFilePath = ""
    @Internal var androidResourcePath = ""
    var rowPositionColumnHeader : Int? = null
    @Internal var outputXmlFileName : String? = null
    @Internal var doNotConvertNewLine : Boolean? = null
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
            outputXmlFileName,
            doNotConvertNewLine = doNotConvertNewLine == true,
        )
    }
}