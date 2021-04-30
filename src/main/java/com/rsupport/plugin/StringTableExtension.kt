package com.rsupport.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property

open class StringTableExtension(project: Project) {

    var googleDriveCredentialPath: Property<String> = project.objects.property(String::class.java)
    var outputXlsxFilePath: Property<String> = project.objects.property(String::class.java)
    var targetSheetUrl: Property<String> = project.objects.property(String::class.java)
    var androidResourcePath: Property<String> = project.objects.property(String::class.java)
    var indexRowNumber: Property<Int> = project.objects.property(Int::class.java)

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

    fun setIndexRowNumber(indexRowNumber: Int) {
        this.indexRowNumber.set(indexRowNumber)
    }

    fun getGoogleDriveCredentialPath(): String = googleDriveCredentialPath.get()

    fun getTargetSheetUrl(): String = targetSheetUrl.get()

    fun getOutputXlsxFilePath(): String = outputXlsxFilePath.get()

    fun getAndroidResourcePath(): String = androidResourcePath.get()

    fun getIndexRowNumber(): Int = indexRowNumber.get()
}