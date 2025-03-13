package com.rsupport.plugin

import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

open class GenerateXmlTask : DefaultTask() {

    @Internal var googleDriveCredentialPath = ""
    @Internal var targetSheetUrl = ""
    @Internal var outputXlsxFilePath = ""
    @Internal var androidResourcePath = ""
    @Internal var rowPositionColumnHeader: Int = 1
    @Internal var defaultLanguageForValues = ""
    @Internal var outputXmlFileName: String = "strings_generated.xml"
    @Internal var doNotConvertNewLine: Boolean = false

    @TaskAction
    fun generateXml() {
        val credential = GoogleCredentials.createCredentials(googleDriveCredentialPath)
        val sheetURLParser = SheetUrlParser(credential, targetSheetUrl)
        StringTableGenerator.generate(
            source = outputXlsxFilePath,
            resPath = androidResourcePath,
            targetSheetName = sheetURLParser.sheetName,
            rowPositionColumnHeader = rowPositionColumnHeader,
            defaultLanguageForValues = defaultLanguageForValues,
            xmlFileName = outputXmlFileName,
            doNotConvertNewLine = doNotConvertNewLine,
        )
    }
}