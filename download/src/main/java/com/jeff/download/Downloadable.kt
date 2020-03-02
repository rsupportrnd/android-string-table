package com.jeff.download

import com.jeff.download.google.drive.GoogleDriveDownload
import java.io.File

interface Downloadable {
    fun execute(): File?

    companion object{
        @JvmStatic
        fun from(credentialsFile: String, fileId: String): Downloadable {
            return GoogleDriveDownload(credentialsFile, fileId)
        }
    }
}