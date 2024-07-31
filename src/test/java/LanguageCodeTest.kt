import com.rsupport.stringtable.LanguageCode
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class LanguageCodeTest {

    @Test
    fun testLanguageCode() {
        val validTestCases = listOf("en", "ko", "en", "ko-KR", "en-CA", "fr", "de", "ja-JP")
        val invalidTestCases = listOf("invalid", "xx", "xx")

        for (testCase in validTestCases) {
            assertTrue("Expected '$testCase' to be valid", LanguageCode.isValid(testCase))
        }

        for (testCase in invalidTestCases) {
            assertFalse("Expected '$testCase' to be invalid", LanguageCode.isValid(testCase))
        }
    }

    @Test
    fun testActualLanguageCode() {
        val validTestCases = mapOf(
            "values-ko" to "ko",
            "values-en" to "en",
            "ko" to "ko",
            "en" to "en",
            "ko-KR" to "ko-KR",
            "en-CA" to "en-CA",
            "values-fr" to "fr",
            "de" to "de",
            "ja-JP" to "ja-JP",
            "values-unknown" to "unknown",
            "values" to null,
            "" to null,
            "xx" to "xx",
        )

        for ((testCase, expected) in validTestCases) {
            val actual = LanguageCode.getActualCode(testCase)
            assertTrue(
                "Expected '$testCase' to be '$expected', but was '$actual'",
                actual == expected
            )
        }
    }
}