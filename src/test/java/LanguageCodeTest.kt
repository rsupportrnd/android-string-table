import com.rsupport.stringtable.LanguageCode
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class LanguageCodeTest {

    @Test
    fun testLanguageCode() {
        val validTestCases = listOf("values-ko", "values-en", "ko", "en", "ko-KR", "en-CA", "values-fr", "de", "ja-JP")
        val invalidTestCases = listOf("invalid", "values-invalid", "xx", "values-xx")

        for (testCase in validTestCases) {
            assertTrue("Expected '$testCase' to be valid", LanguageCode.isValid(testCase))
        }

        for (testCase in invalidTestCases) {
            assertFalse("Expected '$testCase' to be invalid", LanguageCode.isValid(testCase))
        }
    }
}