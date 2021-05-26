package com.rsupport.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class StringTablePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("androidStringTable", StringTableExtension::class.java, project)
        val task = project.tasks.register("generate", StringTableTask::class.java)
        project.afterEvaluate {
            task.get().group = "androidStringTable"
            task.get().googleDriveCredentialPath = extension.googleDriveCredentialPath.get()
            task.get().outputXlsxFilePath = extension.outputXlsxFilePath.get()
            task.get().androidResourcePath = extension.androidResourcePath.get()
            task.get().indexRowNumber = extension.indexRowNumber.get()
            task.get().targetSheetUrl = extension.targetSheetUrl.get()
        }
    }
}