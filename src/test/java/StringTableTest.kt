import com.rsupport.GoogleCredentials
import com.rsupport.SheetUrlParser
import com.rsupport.download.FileDownloader
import com.rsupport.stringtable.StringTableGenerator
import java.io.IOException
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator

class StringTableTest {

    private val credentialFilePath = "example/app/i18n/credentials.json"
    private val sheetUrl =
        "https://docs.google.com/spreadsheets/d/1CTLokrhbVB8Th1l09Bv17QOwlQ-L1yvrcQNg6WB9FZ8/edit#gid=1256465417"
    private val outputXlsxFilePath = "./output/strings_sample.xlsx"
    private val androidResourcePath = "./output"
    private val indexRowNumber = 1

    @Test
    @kotlin.jvm.Throws(IOException::class)
    fun deleteOutput() {
        val path = "./output"
        val deleteFolder = File(path)
        if (deleteFolder.exists()) {
            Files.walk(deleteFolder.toPath())
                .sorted(Comparator.reverseOrder())
                .map { obj: Path -> obj.toFile() }
                .forEach { obj: File -> obj.delete() }
        }
        Assert.assertFalse(deleteFolder.exists())
    }

    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun generateStringXml() {
        val credential = GoogleCredentials.createCredentials(credentialFilePath)
        val sheetURLParser = SheetUrlParser(credential, sheetUrl)
        val source: File? = FileDownloader.download(credential, sheetURLParser.spreadSheetId, outputXlsxFilePath)
        if (source != null) {
            StringTableGenerator.generate(
                outputXlsxFilePath,
                androidResourcePath,
                sheetURLParser.sheetName,
                indexRowNumber
            )
        }
    }
}