package com.rsupport.google.drive

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.rsupport.download.Downloadable
import java.io.*

class GoogleDriveDownload(private val credential: Credential, private val fileId: String, private val filePath: String) : Downloadable {

    companion object {
        private const val APPLICATION_NAME = "구글드라이브 File 다운로드"
        private val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
    }

    override fun execute(): File {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val service = Drive.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build()

        val outputFilePath = service.files().get(fileId).execute().let { driveFile ->
            FileExtension.from(driveFile).let { fileExtension ->
                val exportFile = fileExtension.extension().let { extension ->
                    val name = parsingXlsxFileName()
                    if (extension.isNotEmpty()) "$name.$extension" else name
                }
                val outputDir = makeOutputDirectory()
                FileOutputStream(File("$outputDir/$exportFile")).use {
                    service.files().export(fileId, fileExtension.exportMimeType()).executeAndDownloadTo(it)
                }
                exportFile
            }
        }
        return File(outputFilePath)
    }

    private fun parsingXlsxFileName(): String {
        val dotIndex = filePath.lastIndexOf(".")
        val fileNameIndex = filePath.lastIndexOf("/")
        return filePath.substring(fileNameIndex, dotIndex)
    }

    private fun parsingXlsxFilePath(): String {
        val fileNameIndex = filePath.lastIndexOf("/")
        return filePath.substring(0, fileNameIndex)
    }

    private fun makeOutputDirectory(): String {
        val outputDir = parsingXlsxFilePath()
        File(outputDir).let {
            if (!it.exists()) {
                it.mkdirs()
            }
        }
        return outputDir
    }
}