package com.rsupport.download

import com.google.api.client.auth.oauth2.Credential
import com.rsupport.google.drive.GoogleDriveDownload
import java.io.File

interface Downloadable {
    fun execute(): File?

    companion object {
        @JvmStatic
        fun from(credential: Credential, fileId: String) =
                from(credential, fileId, "")

        fun from(credentials : Credential, fileId: String, filePath: String): Downloadable {
            return GoogleDriveDownload(credentials, fileId, filePath)
        }
    }
}