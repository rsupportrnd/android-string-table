import com.rsupport.download.FileDownloader;
import com.rsupport.stringtable.StringTableGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringTableTest {

    @Test
    public void deleteOutput() throws IOException {
        String path = "./output";
        File deleteFolder = new File(path);

        if(deleteFolder.exists()){
            Files.walk(deleteFolder.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);

        }
        assertFalse(deleteFolder.exists());
    }

    @Test
    public void generateStringXml() throws IOException {
//        File excelFile = FileDownloader.download("./credentials.json", "1CTLokrhbVB8Th1l09Bv17QOwlQ-L1yvrcQNg6WB9FZ8", "strings");
//        StringTableGenerator.generate(excelFile, "./output", "android", "1");
//
//        assertTrue((new File("./output/values/strings_generated.xml")).exists());
    }
}
