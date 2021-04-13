package com.rsupport.plugin

import com.rsupport.download.FileDownloader
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class StringTableTask : DefaultTask() {
    var googleDriveCredentialPath = ""
    var spreadSheetFieldId = ""
    var outputExcelFileName = ""
    var outputResourcePath = ""
    var inputSheetName = ""
    var indexRowNumber = 0

    @TaskAction
    @kotlin.jvm.Throws(Exception::class)
    fun updateStringResource() {
        FileDownloader.main(arrayOf(googleDriveCredentialPath, spreadSheetFieldId, outputExcelFileName))
        val outputFilePath = "./output/$outputExcelFileName"
        StringTableGenerator.main(arrayOf(outputFilePath, outputResourcePath, inputSheetName))
    }
}