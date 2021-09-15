package download

import com.google.api.services.drive.model.File
import com.rsupport.google.drive.FileExtension
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class FileExtensionTest {

    // 구글 드라이브 스프레드시트 형식을때 file 확장자를 찾는것을 확인한다.
    @Test
    fun spreadSheetMimeTypeTest() {
        val driveFile = File().apply {
            mimeType = "application/vnd.google-apps.spreadsheet"
        }
        val fileExtension = FileExtension.from(driveFile)
        val extension = fileExtension.extension()

        MatcherAssert.assertThat("extension 이 xlsx 아 아니라서 실패", extension, Matchers.`is`("xlsx"))
    }

    // 구글 드라이브 스프레드시트 형식을때 내보내기 할 mimeType 을 확인한다.
    @Test
    fun spreadSheetExportMimeTypeTest() {
        val driveFile = File().apply {
            mimeType = "application/vnd.google-apps.spreadsheet"
        }
        val fileExtension = FileExtension.from(driveFile)
        val exportMimeType = fileExtension.exportMimeType()

        MatcherAssert.assertThat("exportMimeType 이 달라서 실패", exportMimeType, Matchers.`is`("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
    }
}