package com.holykss.stringtable;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.holykss.stringtable.StringTableGenerator;

@SuppressWarnings("unused")
public class TestStringTableGeneratorXml {

	@Test
	public void test() throws Exception {
		
		File fileSource = new File("./sample/StringTable.xlsx");
		File resRoot = new File("./sample/res/");
		
		String args[] = new String[]{fileSource.getCanonicalPath(), resRoot.getCanonicalPath()};
		
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
