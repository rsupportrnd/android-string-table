package com.rsupport.plugin

import com.rsupport.download.FileDownloader
import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

open class StringTableTask : DefaultTask() {

    @Internal var googleDriveCredentialPath = ""
    @Internal var targetSheetUrl = ""
    @Internal var outputXlsxFilePath = ""
    @Internal var androidResourcePath = ""
    @Internal var defaultLanguageForValues = ""
    @Internal var rowPositionColumnHeader : Int = 1
    @Internal var outputXmlFileName : String = "strings_generated.xml"
    @Internal var doNotConvertNewLine : Boolean = false
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
            defaultLanguageForValues,
        )
    }
}