package com.rsupport.download.google.drive

import com.google.api.services.drive.model.File

interface FileExtension{
    fun extension(): String
    fun exportMimeType(): String

    companion object{
        fun from(file: File): FileExtension {
            return DriveFileExtension(file)
        }
    }
}

class DriveFileExtension(private val file: File): FileExtension {
    private val extensionMap = mapOf(
            "application/vnd.google-apps.spreadsheet" to "xlsx"
    )

    private val outMimeTypeMap = mapOf(
            "application/vnd.google-apps.spreadsheet" to "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    )

    override fun extension(): String {
        return extensionMap[file.mimeType] ?: ""
    }

    override fun exportMimeType(): String {
        return outMimeTypeMap[file.mimeType] ?: ""
    }
}