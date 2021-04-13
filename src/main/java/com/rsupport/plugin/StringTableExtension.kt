package com.rsupport.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property

open class StringTableExtension(project: Project) {

    var googleDriveCredentialPath: Property<String> = project.objects.property(String::class.java)
    var spreadSheetFieldId: Property<String> = project.objects.property(String::class.java)
    var outputExcelFileName: Property<String> = project.objects.property(String::class.java)
    var outputResourcePath: Property<String> = project.objects.property(String::class.java)
    var inputSheetName: Property<String> = project.objects.property(String::class.java)
    var indexRowNumber: Property<Int> = project.objects.property(Int::class.java)

    fun setGoogleDriveCredentialPath(googleDriveCredentialPath: String?) {
        this.googleDriveCredentialPath.set(googleDriveCredentialPath)
    }

    fun setSpreadSheetFieldId(spreadSheetFieldId: String) {
        this.spreadSheetFieldId.set(spreadSheetFieldId)
    }

    fun setOutputExcelFileName(outputExcelFileName: String) {
        this.outputExcelFileName.set(outputExcelFileName)
    }

    fun setOutputResourcePath(outputResourcePath: String) {
        this.outputResourcePath.set(outputResourcePath)
    }

    fun setInputSheetName(inputSheetName: String) {
        this.inputSheetName.set(inputSheetName)
    }

    fun setIndexRowNumber(indexRowNumber: Int) {
        this.indexRowNumber.set(indexRowNumber)
    }

    fun getGoogleDriveCredentialPath(): String = googleDriveCredentialPath.get()

    fun getSpreadSheetFieldId(): String = spreadSheetFieldId.get()

    fun getOutputExcelFileName(): String = outputExcelFileName.get()

    fun getOutputResourcePath(): String = outputResourcePath.get()

    fun getInputSheetName(): String = inputSheetName.get()

    fun getIndexRowNumber(): Int = indexRowNumber.get()
}