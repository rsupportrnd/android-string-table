package stringtable;

import stringtable.xls.SheetNavigator;
import org.apache.poi.ss.usermodel.Sheet;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.NoSuchElementException;

public class Sheet2Strings {

	public static void convert(Sheet sheet, File pathRes) {
		SheetNavigator nav = new SheetNavigator(sheet);

		int i=1;
		while (true)
		{
			int column = i++;
			try {
				String languageCode = nav.getCell(0, column);
				if (languageCode.contains("values") == false)
					continue;

				String filename = Path.combine(pathRes.getPath(), languageCode, "strings.xml");

				createStringsXml(filename, nav, column);
			}
			catch (NoSuchElementException e)
			{
				break;
			}
		}
	}

	private static void createStringsXml(String filename, SheetNavigator nav, int col) {
		Document doc = new Document();
		Element resources = new Element("resources");
		doc.addContent(resources);

		resources.addContent(new Comment("generator link: https://github.com/jobtools/android-string-table"));
		resources.addContent(new Comment("자동 생성된 파일입니다. 이 xml 파일을 직접 수정하지 마세요!"));
		resources.addContent(new Comment("This file is auto generated. DO NOT EDIT THIS XML FILE!"));

		for (int row=1; true; row++)
		{
			String id;
			String value;

			try {
				id = nav.getCell(row, 0);
				id = id.trim();
				if (id.isEmpty())
					continue;

				// 주석처리
				if (id.startsWith("<") ||
						id.startsWith("/") ||
						id.startsWith("#")) {
					if (id.startsWith("<!--")) {
						id = id.replace("<!--", "").replaceAll("-->", "");
					}

					String commentString = id;
					String description = nav.getCell(row, 1);

					if (!description.isEmpty()) {
						commentString = commentString + " / " + description + " ";
					}

					Comment comment = new Comment(commentString);
					resources.addContent(comment);

					continue;
				}

				try {
					value = nav.getCell(row, col);
				} catch (NoSuchElementException e) {
					value = "";
				}

				if (value.isEmpty()){
					continue;
				}


			}
			catch (NoSuchElementException e)
			{
				break;
			}

			if (id.contains("[]")) {
				Element stringArray = getStringArrayItem(id, value);
				resources.addContent(stringArray);
			} else {
				Element string = new Element("string");
				string.setAttribute("name", id);
				string.setText(getText(value));
				resources.addContent(string);
			}
		}

		try {
			File file = new File(filename);

			if (file.getParentFile().isDirectory() == false)
				file.getParentFile().mkdirs();
			else if (file.exists())
				file.delete();

			// 4. 파일에 출력
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(filename)),
					"UTF-8");
			//FileWriter writer = new FileWriter(filename);
			new XMLOutputter(Format.getPrettyFormat().setIndent("    ").setEncoding("UTF-8")).output(doc, writer);
			writer.close();
			//출처: http://devhome.tistory.com/74 [미주엘의 개발이야기]

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getText(String valueRaw) {
		String value = valueRaw;
		value = value.replace("\'", "\\\'");
		value = value.replace("\\\\\'", "\\\'");
		value = value.replace("\"", "\\\"");
		value = value.replace("\\\\\"", "\\\"");
		value = value.replace("\n", "\\n");

		return value;
	}

	private static Element getStringArrayItem(String id, String value) {
		Element stringArray = new Element("string-array");
		stringArray.setAttribute("name", id.replace("[]", ""));

		String separator = "\n";

		if (value.contains("_x000a_"))
			separator = "_x000a_";

		String []items = value.split(separator);

		for (String item : items)
		{
			Element child = new Element("item");
			child.setText(getText(item));
			stringArray.addContent(child);
		}

		return stringArray;
	}

}
