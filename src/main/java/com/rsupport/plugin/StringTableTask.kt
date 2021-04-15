package com.rsupport.plugin

import com.rsupport.download.FileDownloader
import com.rsupport.stringtable.StringTableGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class StringTableTask : DefaultTask() {
    var googleDriveCredentialPath = ""
    var spreadSheetFieldId = ""
    var outputXlsxFilePath = ""
    var outputResourcePath = ""
    var inputSheetName = ""
    var indexRowNumber = 0

    @TaskAction
    @kotlin.jvm.Throws(Exception::class)
    fun updateStringResource() {
        val source: File?= FileDownloader.download(googleDriveCredentialPath, spreadSheetFieldId, outputXlsxFilePath)
        if (source != null) {
            StringTableGenerator.generate(outputXlsxFilePath, outputResourcePath, inputSheetName, indexRowNumber)
        }
    }
}