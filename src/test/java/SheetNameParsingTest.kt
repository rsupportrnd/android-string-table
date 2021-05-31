import com.rsupport.google.GoogleCredentials
import com.rsupport.google.sheet.SheetUrlParser
import org.junit.Assert
import org.junit.Test
import java.io.IOException
import java.security.GeneralSecurityException

class SheetNameParsingTest {

    private val credentialFilePath = "credentials.json"
    private val sheetUrl =
        "https://docs.google.com/spreadsheets/d/1W6WG_b40FmvyVbstodPgwA6USc0PRANoemCMN66_peM/edit#gid=1886234656"

    @Test
    fun getSheetNameFromSheetUrl() {
        val credential = GoogleCredentials.createCredentials(credentialFilePath)
        val sheetName = SheetUrlParser(credential, sheetUrl).sheetName
        println("sheetName : $sheetName")
        Assert.assertEquals("sample sheet2", sheetName)
    }

    @Test
    fun getSpreadSheetIdFromSheetUrl() {
        val credential = GoogleCredentials.createCredentials(credentialFilePath)
        val spreadSheetId = SheetUrlParser(credential, sheetUrl).spreadSheetId
        println("spreadSheetId : $spreadSheetId")
        Assert.assertEquals("1W6WG_b40FmvyVbstodPgwA6USc0PRANoemCMN66_peM", spreadSheetId)
    }
}