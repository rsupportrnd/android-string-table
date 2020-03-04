package com.jeff.download.google.drive

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.jeff.download.Downloadable
import java.io.*

class GoogleDriveDownload(private val fileId: String) : Downloadable {

    companion object {
        private const val APPLICATION_NAME = "구글드라이브 File 다운로드"
        private val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
        private val scopes = listOf(DriveScopes.DRIVE)
        private const val TOKENS_DIRECTORY_PATH = "drive_all_tokens"
        private const val CREDENTIALS_RESOURCE_PATH = "/credentials/credentials.json"

    }

    override fun execute(): File? {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val service = Drive.Builder(httpTransport, jsonFactory, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build()

        val outputFilePath = service.files().get(fileId).execute().let { driveFile ->
            FileExtension.from(driveFile).let { fileExtension ->
                val exportFile = fileExtension.extension().let {
                    if (it.isNotEmpty()) driveFile.name + "." + it else driveFile.name
                }
                val outputDir = makeOutputDirectory()
                FileOutputStream(File(outputDir + exportFile)).use {
                    service.files().export(fileId, fileExtension.exportMimeType()).executeAndDownloadTo(it)
                }
                exportFile
            }
        }
        return File(outputFilePath)
    }

    private fun makeOutputDirectory(): String{
        val outputDir = "./output/"
        File(outputDir).let {
            if(!it.exists()){
                it.mkdirs()
            }
        }
        return outputDir
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential { // Load client secrets.
        val `in`: InputStream = GoogleDriveDownload::class.java.getResourceAsStream(CREDENTIALS_RESOURCE_PATH)
                ?: throw ClassNotFoundException("Resource not found: $CREDENTIALS_RESOURCE_PATH")

        val clientSecrets = GoogleClientSecrets.load(jsonFactory, InputStreamReader(`in`))
        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

}