package com.rsupport.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class StringTablePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("stringResourceConfig", StringTableExtension::class.java, project)
        val task = project.tasks.register("updateStringResource", StringTableTask::class.java)
        project.afterEvaluate {
            task.get().group = "android"
            task.get().googleDriveCredentialPath = extension.googleDriveCredentialPath!!.get()
            task.get().outputExcelFileName = extension.outputExcelFileName!!.get()
            task.get().inputSheetName = extension.inputSheetName!!.get()
            task.get().outputResourcePath = extension.outputResourcePath!!.get()
            task.get().spreadSheetFieldId = extension.spreadSheetFieldId!!.get()
        }
    }
}