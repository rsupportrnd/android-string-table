package com.rsupport

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets

class SheetUrlParser(private val credential: Credential, sheetURL: String) {

    companion object {
        private const val APPLICATION_NAME = "Google Sheets Title Parser"
    }

    private val urlSplitResult = sheetURL.split("/")
    private val sheetId = urlSplitResult.find { it.contains("#gid") }?.split("=")?.last()?.toInt()
    val spreadSheetId = parsingSpreadSheetId()
    val sheetName = parsingSheetName()

    private fun parsingSpreadSheetId(): String {
        var spreadSheetId = ""
        urlSplitResult.forEachIndexed { index, s ->
            if (s == "d") spreadSheetId = urlSplitResult[index + 1]
        }
        return spreadSheetId
    }

    private fun parsingSheetName(): String {
        var sheetName = ""
        if (sheetId != null) {
            val service = Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
            )
                .setApplicationName(APPLICATION_NAME)
                .build()

            val sheetsList = service.spreadsheets()[spreadSheetId].execute().sheets

            sheetName = sheetsList.map { sheet -> sheet.properties }
                .find { properties -> properties.sheetId == sheetId }?.title.toString()
        }
        return sheetName
    }
}