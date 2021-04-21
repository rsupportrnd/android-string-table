package stringtable

import com.rsupport.stringtable.StringTableGenerator
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.io.FileNotFoundException

class TestStringTableGeneratorXml {
    @Test
    fun test() {
        val fileSource = "./src/test/java/stringtable/sample/StringTable.xlsx"
        val resRoot = "./src/test/java/stringtable/sample/res/"

        StringTableGenerator.generate(fileSource, resRoot, "", 0)
        Assert.assertTrue(File("./src/test/java/stringtable/sample/res/values/strings_generated.xml").exists())
    }

    @Test
    fun `test-5-1`() {
        val fileSource = "./src/test/java/stringtable/sample/string-table-starting-5-1.xlsx"
        val resRoot = "./src/test/java/stringtable/sample/res-5-1/"

        StringTableGenerator.generate(fileSource, resRoot, "", 0)
        Assert.assertTrue(File("./src/test/java/stringtable/sample/res-5-1/values/strings_generated.xml").exists())
    }

    @Test(expected = FileNotFoundException::class)
    fun `strings`() {
        val fileSource = "./src/test/java/stringtable/sample/strings.xlsx"
        val resRoot = "./src/test/java/stringtable/sample/res-strings/"

        StringTableGenerator.generate(fileSource, resRoot, "언어리소스_삼성글로벌 전체", 0)
    }

}