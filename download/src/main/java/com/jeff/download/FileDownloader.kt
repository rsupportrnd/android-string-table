package com.jeff.download

import com.jeff.download.google.drive.GoogleDriveDownload
import java.io.File
import java.lang.IllegalArgumentException

object FileDownloader {

    @JvmStatic
    fun main(args: Array<String>) {

        if (args.size < 2) throw IllegalArgumentException("Not enough arguments.\n" +
                "java -jar ${getRunningJarName()} <credentialsFile> <fileId> [output-filename]")

        val credentialsFile = args[0]
        val fileId = args[1]
        val filename = if (args.size > 2) args[2] else null

        val downloadable = GoogleDriveDownload(credentialsFile, fileId, filename)

        downloadable.execute()
    }

    private fun getRunningJarName() =
            File(FileDownloader.javaClass.protectionDomain.codeSource.location.toURI().path).name
}