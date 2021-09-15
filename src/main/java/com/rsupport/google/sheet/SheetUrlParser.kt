package com.rsupport.google.sheet

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
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
        urlSplitResult.forEachIndexed { index, s ->
            if (s == "d") {
                return urlSplitResult[index + 1]
            }
        }
        throw IllegalStateException("Can not parsing spread sheet Id. Check the URL again.")
    }

    private fun parsingSheetName(): String {
        if (spreadSheetId.isEmpty()) return ""
        if (sheetId == null) return ""

        val service = Sheets.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JacksonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName(APPLICATION_NAME)
            .build()

        try {
            val sheetsList = service.spreadsheets()[spreadSheetId].execute().sheets

            return sheetsList.first { sheet -> sheet.properties.sheetId == sheetId }.properties.title.toString()
        } catch (e: GoogleJsonResponseException) {
            e.printStackTrace()
        }
        return ""
    }
}