package stringtable

import com.rsupport.stringtable.StringTableGenerator
import org.junit.Test
import java.io.File
import java.security.InvalidParameterException

class TestStringTableGeneratorXml {
    @Test
    fun test() {
        val fileSource = File("./src/test/java/stringtable/sample/StringTable.xlsx")
        val resRoot = File("./src/test/java/stringtable/sample/res/")
        val pathSource = getPathWithFile(fileSource)
        val pathRes = getPathWithFile(resRoot)
        val args = listOf(
                pathSource,
                pathRes)
        StringTableGenerator.main(args.toTypedArray())
    }

    private fun getPathWithFile(file: File): String {
        if (file.exists()) {
            return file.absolutePath
        }
        return file.canonicalPath
    }

    @Test(expected = InvalidParameterException::class)
    fun singleParameterShouldThrowsInvalidParameterException() {
        StringTableGenerator.main(arrayOf(""))

    }

    @Test
    fun `test-5-1`() {
        val fileSource = File("./src/test/java/stringtable/sample/string-table-starting-5-1.xlsx")
        val resRoot = File("./src/test/java/stringtable/sample/res-5-1/")
        val pathSource = getPathWithFile(fileSource)
        val pathRes = getPathWithFile(resRoot)
        val args = listOf(
                pathSource,
                pathRes)
        StringTableGenerator.main(args.toTypedArray())
    }

    @Test
    fun `strings`() {
        val fileSource = File("./src/test/java/stringtable/sample/strings.xlsx")
        val resRoot = File("./src/test/java/stringtable/sample/res-strings/")
        val pathSource = getPathWithFile(fileSource)
        val pathRes = getPathWithFile(resRoot)
        val args = listOf(
                pathSource,
                pathRes,
                "언어리소스_삼성글로벌 전체"
        )
        StringTableGenerator.main(args.toTypedArray())
    }

}