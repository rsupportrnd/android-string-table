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
        val source = FileDownloader.download(googleDriveCredentialPath, spreadSheetFieldId, outputExcelFileName)
        if (source != null) {
            StringTableGenerator.generate(source, outputResourcePath, inputSheetName, indexRowNumber)
        }
    }
}