package com.rsupport.google

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import com.google.api.client.auth.oauth2.Credential
import com.google.api.services.drive.DriveScopes

object GoogleCredentials {
    private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    private const val TOKENS_DIRECTORY_NAME = "drive_all_tokens"
    private val SCOPES = listOf(DriveScopes.DRIVE, SheetsScopes.DRIVE_READONLY)
    private val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()

    fun createCredentials(credentialFilePath: String): Credential {
        if(credentialFilePath.isEmpty()) throw IllegalStateException("Credential File Path is Empty.")
        val credentialFile = File(credentialFilePath)
        // Store tokens next to credentials.json so the path is stable across Gradle
        // daemon restarts and runner environments (relative paths resolve against the
        // daemon JVM CWD, which varies by Gradle version).
        val tokensDir = File(credentialFile.parentFile ?: File("."), TOKENS_DIRECTORY_NAME)
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(FileInputStream(credentialFile)))
        val flow = GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(FileDataStoreFactory(tokensDir))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        val credential = AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
        if(credential != null) {
            return credential
        }
        throw IllegalStateException("Can not create credential file. Check the path or file again.")
    }
}