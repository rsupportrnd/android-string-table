package com.rsupport.plugin

import com.rsupport.download.FileDownloader
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

open class StringTableTask : DefaultTask() {
    @Input
    var googleDriveCredentialPath = ""
    @Input
    var spreadSheetFieldId = ""
    @Input
    var outputExcelFilePath = ""
    @Input
    var outputExcelFileName = ""
    @Input
    var outputResourcePath = ""
    @Input
    var inputSheetName = ""
    @Input
    var indexRowNumber = 0

    @TaskAction
    @kotlin.jvm.Throws(Exception::class)
    fun updateStringResource() {
        val source: File?= FileDownloader.download(googleDriveCredentialPath, spreadSheetFieldId, outputExcelFileName, outputExcelFilePath)
        if (source != null) {
            StringTableGenerator.generate("$outputExcelFilePath/$outputExcelFileName.xlsx", outputResourcePath, inputSheetName, indexRowNumber)
        }
    }
}