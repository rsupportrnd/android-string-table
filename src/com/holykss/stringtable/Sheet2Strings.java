package com.holykss.stringtable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Sheet;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.holykss.stringtable.xls.SheetNavigator;

public class Sheet2Strings {

	public static boolean isStringTable(Sheet sheet) {
		SheetNavigator navigator = new SheetNavigator(sheet);
		return navigator.getCell(0, 0).equals("string id");
	}

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
				
				String filename = Path.combine(pathRes.getPath(), languageCode, "strings_generated.xml");
				
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
		
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
//		sb.append("<resources>\n");
//		sb.append("\n");
		
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
						id.startsWith("#") ||
						false
						) {
					if (id.startsWith("<!--")) {
						id = id.replace("<!--", "").replaceAll("-->", "");
					}
					Comment comment = new Comment(id);
					resources.addContent(comment);

					continue;
				}

				try {
					value = nav.getCell(row, col);
				} catch (NoSuchElementException e) {
					value = "";
				}

				if (value.isEmpty())
					value = getProperValue(nav, row, col);
				
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
				if (isNotFormatted(value)) {
					string.setAttribute("formatted", "false");
				}
				string.setText(getText(value));
				resources.addContent(string);
//				
//				item = "    <string formatted=\"false\" name=\"" + id + "\">" + 
//						getValue(value) + "</string>\n";
			}
		}

		try {
			File file = new File(filename);
			
			if (file.getParentFile().isDirectory() == false)
				file.getParentFile().mkdirs();
			else if (file.exists())
				file.delete();
			
			// 4. 파일에 출력
			FileWriter writer = new FileWriter(filename);
			new XMLOutputter(Format.getPrettyFormat()).output(doc, writer);
			writer.close();
			//출처: http://devhome.tistory.com/74 [미주엘의 개발이야기]
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getText(String valueRaw) {
		String value = valueRaw;
		value = value.replace("\'", "\\'");
		value = value.replace("\\\\\'", "\\'");
		
//		if (needCoveredWithCdata(value)) {
//			return "<![CDATA[" + value + "]]>";
//		}
//		
		return value;
	}

	private static boolean isNotFormatted(String value) {
		return 
			value.contains("\'") ||
			value.contains("\n") ||
			false;
	}
	private static boolean needCoveredWithCdata(String value) {
		if (value.startsWith("<![CDATA")) {
			return false;
		}
		
		if (value.startsWith("<")) {
			return true;
		}
		
//		if (value.contains("%") ||
//				value.contains("%%") ||
//				value.contains("<") ||
//				value.contains(">") ||
//				value.contains("&") ||
//				value.contains("\'") ||
//				value.contains("...") ||
//				value.contains("\n") ||
//				false
//				)
//			return true;
		
		return false;
	}

//	private static String getXmlString(String value) {
//		if (true) {
//			return value;
//		}
//		
//		String changeTable[][] = new String[][]{ 
//			{"&", "&amp;"},
//			{"\'", "\\\'"},
//			{"...", "&#8230;"},
//			{"\n", "\\n"},
//			
//		};
//		
//		String result = new String(value);
//		
//		for (String[] set:changeTable)
//		{
//			if (result.contains(set[0]))
//			{
//				result = result.replace(set[0], set[1]);
//			}
//		}
//		return result;
//	}

	private static String getProperValue(SheetNavigator nav, int row, int col) {
		while (col-- > 0)
		{
			try {
				String cell = nav.getCell(row, col);
				
				if (cell.isEmpty() == false)
					return cell;
			} catch (NoSuchElementException e) {
				continue;
			}
			
		}
		throw new NullPointerException("It'll never happening");
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
			if (isNotFormatted(item)) {
				child.setAttribute("formatted", "false");
			}
			child.setText(getText(item));
			stringArray.addContent(child);
		}

		return stringArray;
	}

}
