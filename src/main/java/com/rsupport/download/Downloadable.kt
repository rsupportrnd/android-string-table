package com.rsupport.download

import com.rsupport.download.google.drive.GoogleDriveDownload
import java.io.File

interface Downloadable {
    fun execute(): File?

    companion object {
        @JvmStatic
        fun from(credentialsFile: String, fileId: String, filePath: String) =
                from(credentialsFile, fileId, null, filePath)

        fun from(credentialsFile: String, fileId: String, filename: String?, filePath: String): Downloadable {
            return GoogleDriveDownload(credentialsFile, fileId, filename, filePath)
        }
    }
}