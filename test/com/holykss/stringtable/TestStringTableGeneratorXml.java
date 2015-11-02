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
		
		StringTableGenerator.main(args);
	}

}
