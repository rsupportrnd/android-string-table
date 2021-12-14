package com.rsupport.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property
import javax.annotation.Nullable

open class StringTableExtension(project: Project) {

    var googleDriveCredentialPath: Property<String> = project.objects.property(String::class.java)
    var outputXlsxFilePath: Property<String> = project.objects.property(String::class.java)
    var targetSheetUrl: Property<String> = project.objects.property(String::class.java)
    var androidResourcePath: Property<String> = project.objects.property(String::class.java)
    var rowPositionColumnHeader: Property<Int>? = project.objects.property(Int::class.java)
    var outputXmlFileName: Property<String>? = project.objects.property(String::class.java)

    fun setGoogleDriveCredentialPath(googleDriveCredentialPath: String?) {
        this.googleDriveCredentialPath.set(googleDriveCredentialPath)
    }

    fun setTargetSheetUrl(targetSheetUrl: String) {
        this.targetSheetUrl.set(targetSheetUrl)
    }

    fun setOutputXlsxFilePath(outputXlsxFilePath: String) {
        this.outputXlsxFilePath.set(outputXlsxFilePath)
    }

    fun setAndroidResourcePath(androidResourcePath: String) {
        this.androidResourcePath.set(androidResourcePath)
    }

    fun setrowPositionColumnHeader(rowPositionColumnHeader: Int?) {
        this.rowPositionColumnHeader?.set(rowPositionColumnHeader)
    }

    fun setOutputXmlFileName(outputXmlFileName: String?) {
        this.outputXmlFileName?.set(outputXmlFileName)
    }

    fun getGoogleDriveCredentialPath(): String = googleDriveCredentialPath.get()

    fun getTargetSheetUrl(): String = targetSheetUrl.get()

    fun getOutputXlsxFilePath(): String = outputXlsxFilePath.get()

    fun getAndroidResourcePath(): String = androidResourcePath.get()

    fun getrowPositionColumnHeader(): Int? = rowPositionColumnHeader?.get()

    fun getOutputXmlFileName(): String? = outputXmlFileName?.get()
}