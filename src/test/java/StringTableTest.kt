import com.rsupport.download.FileDownloader
import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import com.rsupport.stringtable.StringTableGenerator
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class StringTableTest {

    private val credentialFilePath = "credentials.json"
    private val sheetUrl =
        "https://docs.google.com/spreadsheets/d/1W6WG_b40FmvyVbstodPgwA6USc0PRANoemCMN66_peM/edit#gid=1886234656"
    private val outputXlsxFilePath = "./output/strings_sample.xlsx"
    private val androidResourcePath = "./output"

    private fun deleteOutput() {
        val path = "./output"
        val deleteFolder = File(path)
        if (deleteFolder.exists()) {
            Files.walk(deleteFolder.toPath())
                .sorted(Comparator.reverseOrder())
                .map { obj: Path -> obj.toFile() }
                .forEach { obj: File -> obj.delete() }
        }
    }

    @Test
    fun generateStringXml() {
        deleteOutput()
        val credential = GoogleCredentials.createCredentials(credentialFilePath)
        val sheetURLParser = SheetUrlParser(credential, sheetUrl)
        val source = FileDownloader.download(credential, sheetURLParser.spreadSheetId, outputXlsxFilePath)
        if(source != null) {
            StringTableGenerator.generate(
                outputXlsxFilePath,
                androidResourcePath,
                sheetURLParser.sheetName,
                1,
                null
            )
        }
        Assert.assertTrue(File("$androidResourcePath/values/strings_generated.xml").exists())
    }

    @Test(expected = java.lang.AssertionError::class)
    fun `index row number 가 잘못되었을 때 AssertionError 가 발생해야한다`() {
        deleteOutput()
        val credential = GoogleCredentials.createCredentials(credentialFilePath)
        val sheetURLParser = SheetUrlParser(credential, sheetUrl)
        val source = FileDownloader.download(credential, sheetURLParser.spreadSheetId, outputXlsxFilePath)
        if(source != null) {
            StringTableGenerator.generate(
                outputXlsxFilePath,
                androidResourcePath,
                sheetURLParser.sheetName,
                2,
                null
            )
        }
    }

    @Test(expected = IllegalStateException::class)
    fun generateStringXmlWhenSpreadSheetIdIsEmpty() {
        println("generateStringXmlWhenSpreadSheetIdIsEmpty")
        deleteOutput()
        val sheetUrlSpreadSheetIdEmpty = "https://docs.google.com/spreadsheets/edit#gid=1886234656"
        val credential = GoogleCredentials.createCredentials(credentialFilePath)
        val sheetURLParser = SheetUrlParser(credential, sheetUrlSpreadSheetIdEmpty)
        val source = FileDownloader.download(credential, sheetURLParser.spreadSheetId, outputXlsxFilePath)
        if(source != null) {
            StringTableGenerator.generate(
                outputXlsxFilePath,
                androidResourcePath,
                sheetURLParser.sheetName,
                1,
                null
            )
        }
    }
}