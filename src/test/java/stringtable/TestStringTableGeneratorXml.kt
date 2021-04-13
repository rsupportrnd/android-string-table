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
}