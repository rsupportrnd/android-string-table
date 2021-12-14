package com.rsupport.plugin

import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class GenerateXmlTask : DefaultTask() {

    var googleDriveCredentialPath = ""
    var targetSheetUrl = ""
    var outputXlsxFilePath = ""
    var androidResourcePath = ""
    var rowPositionColumnHeader : Int? = null
    var outputXmlFileName : String? = null

    @TaskAction
    fun generateXml() {
        val credential = GoogleCredentials.createCredentials(googleDriveCredentialPath)
        val sheetURLParser = SheetUrlParser(credential, targetSheetUrl)
        StringTableGenerator.generate(
            outputXlsxFilePath,
            androidResourcePath,
            sheetURLParser.sheetName,
            rowPositionColumnHeader,
            outputXmlFileName
        )
    }
}