package com.jeff.download

import com.jeff.download.google.drive.GoogleDriveDownload
import java.io.File

interface Downloadable {
    fun execute(): File?

    companion object{
        @JvmStatic
        fun from(fileId: String): Downloadable {
            return GoogleDriveDownload(fileId)
        }
    }
}