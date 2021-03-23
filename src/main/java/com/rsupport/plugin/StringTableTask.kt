package com.rsupport.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import com.rsupport.download.FileDownloader
import com.rsupport.stringtable.StringTableGenerator
import java.lang.Exception

class StringTableTask : DefaultTask() {
    var googleDriveCredentialPath: String? = null
    var spreadSheetFieldId: String? = null
    var outputExcelFileName: String? = null
    var inputExcelFilePath: String? = null
    var outputResourcePath: String? = null
    var inputSheetName: String? = null
    @TaskAction
    @Throws(Exception::class)
    fun updateStringResource() {
        FileDownloader.main(arrayOf(googleDriveCredentialPath, spreadSheetFieldId, outputExcelFileName))
        StringTableGenerator.main(arrayOf(inputExcelFilePath, outputResourcePath, inputSheetName))
    }
}