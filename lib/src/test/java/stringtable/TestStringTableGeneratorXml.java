package stringtable;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import stringtable.StringTableGenerator;

@SuppressWarnings("unused")
public class TestStringTableGeneratorXml {

	@Test
	public void test() throws Exception {
		
		File fileSource = new File("./src/test/java/stringtable/sample/StringTable.xlsx");
		File resRoot = new File("./src/test/java/stringtable/sample/res/");
		
		
		String pathSource;
		String pathRes;
		
		pathSource = getPathWithFile(fileSource);
		pathRes = getPathWithFile(resRoot);

		String args[] = new String[]{
				pathSource, 
				pathRes};
		
		StringTableGenerator.main(args);
	}

	private String getPathWithFile(File file) {
		if (file.exists()) {
			return file.getAbsolutePath();
		} 
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
