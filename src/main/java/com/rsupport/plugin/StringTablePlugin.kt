package com.rsupport.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class StringTablePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("androidStringTable", StringTableExtension::class.java, project)

        val downloadExcelFileTask = project.tasks.register("downloadSpreadsheet", DownloadExcelFileTask::class.java)
        project.afterEvaluate {
            downloadExcelFileTask.get().group = "androidStringTable"
            downloadExcelFileTask.get().googleDriveCredentialPath = extension.googleDriveCredentialPath.get()
            downloadExcelFileTask.get().outputXlsxFilePath = extension.outputXlsxFilePath.get()
            downloadExcelFileTask.get().targetSheetUrl = extension.targetSheetUrl.get()
        }

        val generateXmlTask = project.tasks.register("generateStringsXmls", GenerateXmlTask::class.java)
        project.afterEvaluate {
            generateXmlTask.get().group = "androidStringTable"
            generateXmlTask.get().googleDriveCredentialPath = extension.googleDriveCredentialPath.get()
            generateXmlTask.get().outputXlsxFilePath = extension.outputXlsxFilePath.get()
            generateXmlTask.get().androidResourcePath = extension.androidResourcePath.get()
            generateXmlTask.get().rowPositionColumnHeader = extension.rowPositionColumnHeader?.get()
            generateXmlTask.get().defaultLanguageForValues = extension.defaultLanguageForValues?.get()
            generateXmlTask.get().targetSheetUrl = extension.targetSheetUrl.get()
            generateXmlTask.get().outputXmlFileName = extension.outputXmlFileName?.get()
            generateXmlTask.get().doNotConvertNewLine = extension.doNotConvertNewLine?.get()
        }

        val updateResourceTask = project.tasks.register("updateResource", StringTableTask::class.java)
        project.afterEvaluate {
            updateResourceTask.get().group = "androidStringTable"
            updateResourceTask.get().googleDriveCredentialPath = extension.googleDriveCredentialPath.get()
            updateResourceTask.get().outputXlsxFilePath = extension.outputXlsxFilePath.get()
            updateResourceTask.get().androidResourcePath = extension.androidResourcePath.get()
            updateResourceTask.get().rowPositionColumnHeader = extension.rowPositionColumnHeader?.get()
            updateResourceTask.get().defaultLanguageForValues = extension.defaultLanguageForValues?.get()
            updateResourceTask.get().targetSheetUrl = extension.targetSheetUrl.get()
            updateResourceTask.get().outputXmlFileName = extension.outputXmlFileName?.get()
            updateResourceTask.get().doNotConvertNewLine = extension.doNotConvertNewLine?.get()
        }

    }
}