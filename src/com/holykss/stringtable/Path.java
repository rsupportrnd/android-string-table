package com.holykss.stringtable;

import java.io.File;
import java.io.IOException;

public class Path {
	public static String combine(File path, String childName) {
		String pathString;
		try {
			pathString = path.getCanonicalPath();
		} catch (IOException e) {
			pathString = path.getPath();
			e.printStackTrace();
		}
		return combine(pathString, childName);
	}

	public static String combine(String pathString, String childName) {
		String pathDelimiter = getDelimiter(pathString + childName);

		String result = pathString;

		if (pathString.endsWith(pathDelimiter))
			result = pathString;
		else
			result = pathString + pathDelimiter;

		if (childName.startsWith(pathDelimiter))
			result = result + childName.substring(1);
		else
			result = result + childName;

		return result;
	}

	private static String getDelimiter(String path) {
		if (path.indexOf('\\') != -1)
			return "\\";
		else
			return "/";
	}

	public static String combine(String pathString, String childName1, String... filenames) {
		String result = combine(pathString, childName1);

		for (String filename : filenames) {
			result = combine(result, filename);
		}
		return result;
	}
}
