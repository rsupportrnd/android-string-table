package com.holykss.stringtable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Sheet;

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
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<resources>\n");
		sb.append("\n");
		
		for (int row=1; true; row++)
		{
			String id;
			String value;
			
			try {
				id = nav.getCell(row, 0);
				if (id.isEmpty())
					continue;

				value = nav.getCell(row, col);
				if (value.isEmpty())
					value = getProperValue(nav, row, col);
				
			}
			catch (Exception e)
			{
				break;
			}
			
			String item;
			
			if (isStringArray(id))
			{
				sb.append("\n");
				item = getStringArrayItem(id, value);
			}
			else
			{	
				String formattedTag = getFormattedTag(value);
				
				item = "\t<string " + formattedTag + "name=\"" + id + "\">" + getXmlString(value) + "</string>\n";
			}
			
			sb.append(item);
		}

		sb.append("\n");
		sb.append("\n");
		sb.append("</resources>");
		
		File file = new File(filename);
		
		if (file.getParentFile().isDirectory() == false)
			file.getParentFile().mkdirs();
		else if (file.exists())
			file.delete();

		try {
			Writer fwriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			fwriter.write(sb.toString());
			fwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getFormattedTag(String value) {
		if (!value.contains("%") || value.contains("%%"))
			return "";
		
		return "formatted=\"false\" ";
	}

	private static String getXmlString(String value) {
		String changeTable[][] = new String[][]{ 
			{"&", "&amp;"},
			{"\'", "\\\'"},
			{"...", "&#8230;"},
			{"\n", "\\n"},
			
		};
		
		String result = new String(value);
		
		for (String[] set:changeTable)
		{
			if (result.contains(set[0]))
			{
				result = result.replace(set[0], set[1]);
			}
		}
		return result;
	}

	private static String getProperValue(SheetNavigator nav, int row, int col) {
		while (col-- > 0)
		{
			String cell = nav.getCell(row, col);
			
			if (cell.isEmpty() == false)
				return cell;
		}
		throw new NullPointerException("It'll never happening");
	}

	private static boolean isStringArray(String id) {
		return id.indexOf("[]") >= 0;
	}

	private static String getStringArrayItem(String id, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t<string-array name=\"" + id.replace("[]", "") + "\">\n");
		
		String separator = "\n";
		
		if (value.contains("_x000a_"))
			separator = "_x000a_";
		
		String []items = value.split(separator);
		
		for (String item : items)
		{
			sb.append("\t\t<item>" + getXmlString(item) + "</item>\n");
		}
		sb.append("\t</string-array>\n");
		
		return sb.toString();
	}

}
