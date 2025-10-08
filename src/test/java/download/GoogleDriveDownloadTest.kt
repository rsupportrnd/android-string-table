package download

import com.rsupport.google.GoogleCredentials
import com.rsupport.download.FileDownloader
import org.junit.Assert
import org.junit.Assume
import org.junit.Before
import org.junit.Test
import java.io.File

class GoogleDriveDownloadTest {
    private val credentialFilePath = "credentials.json"

    @Before
    fun checkCredentials() {
        Assume.assumeTrue(
            "Skipping test: credentials.json not found. This test requires Google API credentials.",
            File(credentialFilePath).exists()
        )
    }

    @Test
    fun `sample`() {
        val credential = GoogleCredentials.createCredentials(credentialFilePath)
        FileDownloader.download(credential, "12hmQ7U0npYM4hK4ck3qN9bMUDRu-ZcPueluxz5X4w30", "./output/sample.xlsx")
        Assert.assertTrue(File("./output/sample.xlsx").exists())
    }
}