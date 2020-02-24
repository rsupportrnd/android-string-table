package stringtable.xls;

public class NameExtractor {
	public static String extractSheetname(String pattern, String language) {
		int lastIndexOfSlash = pattern.lastIndexOf('/');
		if (lastIndexOfSlash == -1)
			return "*";
		
		String sheetNameWithTag = pattern.substring(lastIndexOfSlash + 1);
		String sheetName = replaceTags(sheetNameWithTag,
				TAG_LANGUAGE, language);

		return sheetName;
	}

	public static final String TAG_LANGUAGE = "${Language}";
	public static final String DefaultXlsxExtension = ".xlsx";

	public static String extractFilename(String pattern, String language) {
		int lastIndexOfSlash = pattern.lastIndexOf('/');
		if (lastIndexOfSlash == -1)
			lastIndexOfSlash = pattern.length();
		
		String filenameWithTag = pattern.substring(0, lastIndexOfSlash);
		String filename = replaceTags(filenameWithTag, TAG_LANGUAGE,
				language);

		return getRealFilenameWithExtension(filename, DefaultXlsxExtension);
	}

	private static String getRealFilenameWithExtension(String filename,
			String defaultExtension) {
		int lastDotIndex = filename.lastIndexOf('.');
		if (lastDotIndex > 0) {
			String extension = filename.substring(lastDotIndex);

			// .이후가 디폴트 확장자면
			if (extension.equalsIgnoreCase(defaultExtension))
				return filename;
		}
		return filename + defaultExtension;
	}

	public static String replaceTags(String original, String find,
			String replace) {
		String result = original;
		while (result.contains(find)) {
			result = result.replace(find, replace);
		}
		return result;
	}

}