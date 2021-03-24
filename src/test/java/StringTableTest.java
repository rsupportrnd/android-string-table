import com.rsupport.download.FileDownloader;
import com.rsupport.stringtable.StringTableGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StringTableTest {

    @Test
    public void sample() throws IOException {
        File excelFile = FileDownloader.download("./credentials.json", "1CTLokrhbVB8Th1l09Bv17QOwlQ-L1yvrcQNg6WB9FZ8", "strings");
        StringTableGenerator.generate(excelFile, "./output", "android", 1);
    }
}
