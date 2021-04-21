import com.rsupport.download.FileDownloader.download
import com.rsupport.stringtable.StringTableGenerator.generate
import java.io.IOException
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator

class StringTableTest {
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
        val excelFile = download("./credentials.json", "1CTLokrhbVB8Th1l09Bv17QOwlQ-L1yvrcQNg6WB9FZ8", "strings", "./output")
        if (excelFile != null) {
            generate("./output/strings.xlsx", "./output", "android", 1)
        }
        Assert.assertTrue(File("./output/values/strings_generated.xml").exists())
    }
}