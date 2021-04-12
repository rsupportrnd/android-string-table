package com.rsupport.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property

class StringTableExtension(project: Project) {
    @JvmField
    var googleDriveCredentialPath: Property<String> = project.objects.property(String::class.java)

    @JvmField
    var spreadSheetFieldId: Property<String> = project.objects.property(String::class.java)

    @JvmField
    var outputExcelFileName: Property<String> = project.objects.property(String::class.java)

    @JvmField
    var outputResourcePath: Property<String> = project.objects.property(String::class.java)

    @JvmField
    var inputSheetName: Property<String> = project.objects.property(String::class.java)

    @JvmField
    var indexRowNumber: Property<String> = project.objects.property(String::class.java)
    fun setGoogleDriveCredentialPath(googleDriveCredentialPath: String?) {
        this.googleDriveCredentialPath.set(googleDriveCredentialPath)
    }

    fun setSpreadSheetFieldId(spreadSheetFieldId: String?) {
        this.spreadSheetFieldId.set(spreadSheetFieldId)
    }

    fun setOutputExcelFileName(outputExcelFileName: String?) {
        this.outputExcelFileName.set(outputExcelFileName)
    }

    fun setOutputResourcePath(outputResourcePath: String?) {
        this.outputResourcePath.set(outputResourcePath)
    }

    fun setInputSheetName(inputSheetName: String?) {
        this.inputSheetName.set(inputSheetName)
    }

    fun setIndexRowNumber(indexRowNumber: String?) {
        this.indexRowNumber.set(indexRowNumber)
    }

}