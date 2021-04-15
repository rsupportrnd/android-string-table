package download

import com.rsupport.download.FileDownloader
import org.junit.Assert
import org.junit.Test
import java.io.File

class GoogleDriveDownloadTest {
    @Test
    fun `sample`() {
        FileDownloader.download("./credentials.json", "12hmQ7U0npYM4hK4ck3qN9bMUDRu-ZcPueluxz5X4w30", "sample", "./output")
        Assert.assertTrue(File("./output/sample.xlsx").exists())
    }
}