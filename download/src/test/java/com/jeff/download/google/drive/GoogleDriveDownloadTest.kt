package com.jeff.download.google.drive

import com.jeff.download.FileDownloader
import org.junit.Test
import java.lang.IllegalArgumentException

class GoogleDriveDownloadTest {

    @Test(expected = IllegalArgumentException::class)
    fun `argument 부족`() {

        FileDownloader.main(arrayOf("xxx"))
    }

    @Test
    fun `sample`() {
        FileDownloader.main(arrayOf("./credentials.json", "12hmQ7U0npYM4hK4ck3qN9bMUDRu-ZcPueluxz5X4w30", "sample"))
    }

}