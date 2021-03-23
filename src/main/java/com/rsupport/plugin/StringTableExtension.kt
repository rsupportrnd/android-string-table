package com.rsupport.plugin

import org.gradle.api.provider.Property

class StringTableExtension {
    @JvmField
    var googleDriveCredentialPath: Property<String>? = null
    @JvmField
    var spreadSheetFieldId: Property<String>? = null
    @JvmField
    var outputExcelFileName: Property<String>? = null
    @JvmField
    var inputExcelFilePath: Property<String>? = null
    @JvmField
    var outputResourcePath: Property<String>? = null
    @JvmField
    var inputSheetName: Property<String>? = null
}