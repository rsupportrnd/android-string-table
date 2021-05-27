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
            generateXmlTask.get().indexRowNumber = extension.indexRowNumber.get()
            generateXmlTask.get().targetSheetUrl = extension.targetSheetUrl.get()
            generateXmlTask.get().outputXmlFileName = extension.outputXmlFileName.get()
        }

        val downloadAndGenerateTask = project.tasks.register("downloadSheetAndGenerateXmls", StringTableTask::class.java)
        project.afterEvaluate {
            downloadAndGenerateTask.get().group = "androidStringTable"
            downloadAndGenerateTask.get().googleDriveCredentialPath = extension.googleDriveCredentialPath.get()
            downloadAndGenerateTask.get().outputXlsxFilePath = extension.outputXlsxFilePath.get()
            downloadAndGenerateTask.get().androidResourcePath = extension.androidResourcePath.get()
            downloadAndGenerateTask.get().indexRowNumber = extension.indexRowNumber.get()
            downloadAndGenerateTask.get().targetSheetUrl = extension.targetSheetUrl.get()
            downloadAndGenerateTask.get().outputXmlFileName = extension.outputXmlFileName.get()
        }

    }
}