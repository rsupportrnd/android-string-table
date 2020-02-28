package stringtable

import org.junit.Test
import java.io.File
import java.io.IOException
import java.security.InvalidParameterException

class TestStringTableGeneratorXml {
    @Test
    fun test() {
        val fileSource = File("./src/test/java/stringtable/sample/StringTable.xlsx")
        val resRoot = File("./src/test/java/stringtable/sample/res/")
        val pathSource: String?
        val pathRes: String?
        pathSource = getPathWithFile(fileSource)
        pathRes = getPathWithFile(resRoot)
        val args = arrayOf(
                pathSource,
                pathRes)
        StringTableGenerator.main(args)
    }

    private fun getPathWithFile(file: File): String? {
        if (file.exists()) {
            return file.absolutePath
        }
        try {
            return file.canonicalPath
        } catch (e: IOException) { // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return null
    }

    @Test(expected = InvalidParameterException::class)
    fun singleParameterShouldThrowsInvalidParameterException() {
        StringTableGenerator.main(arrayOf(""))

    }
}