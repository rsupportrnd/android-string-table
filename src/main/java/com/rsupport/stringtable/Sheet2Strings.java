package com.rsupport.stringtable;

import com.rsupport.stringtable.xls.SheetNavigator;
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

    private static Sheet2Strings instance;

    private Sheet2Strings() {
    }

    public static Sheet2Strings getInstance() {
        if (instance == null) {
            instance = new Sheet2Strings();
        }
        return instance;
    }

    public void convert(Sheet sheet, File pathRes) {
        SheetNavigator nav = new SheetNavigator(sheet);
        int columnStringId = findColumnId(nav);

        int i = 1;
        while (true) {
            int column = i++;
            try {
                String languageCode = nav.getCell(0, column);
                if (!languageCode.contains("values")) continue;
                String fileName = Path.combine(pathRes.getPath(), languageCode, "strings_generated.xml");
                createStringsXml(fileName, nav, columnStringId, column);
            } catch (NoSuchElementException e) {
                break;
            }
        }

    }

    private int findColumnId(SheetNavigator nav) {
        int result = 0;
        while (true) {
            result++;
            try {
                if (isIdColumn(nav.getCell(0, result).toLowerCase())) {
                    return result;
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
        return 0;
    }

    private Boolean isIdColumn(String columnHeaderName) {
        if (columnHeaderName.startsWith("id ")) {
            return true;
        }
        if (columnHeaderName.endsWith(" id")) {
            return true;
        }
        if (columnHeaderName.equals("id")
                || columnHeaderName.equals("ids")
                || columnHeaderName.equals("identification")
                || columnHeaderName.equals("identifications")) {
            return true;
        }
        return false;
    }

    private void createStringsXml(String fileName, SheetNavigator nav, int columnStringId, int col) {
        final Document doc = new Document();
        final Element resources = new Element("resources");
        doc.addContent(resources);
        resources.addContent(new Comment("generator link: https://github.com/jobtools/android-string-table "));
        resources.addContent(new Comment("자동 생성된 파일입니다. 이 xml 파일을 직접 수정하지 마세요!"));
        resources.addContent(new Comment("This file is auto generated. DO NOT EDIT THIS XML FILE!"));

        int row = 1;
        while (true) {
            String id;
            String value;
            try {
                id = nav.getCell(row, columnStringId);
                id = id.trim();
                if (id.isEmpty()) {
                    row++;
                    continue;
                }

                // 주석처
                if (id.startsWith("<") || id.startsWith("/") || id.startsWith("#")) {
                    if (id.startsWith("<!--")) {
                        id = id.replace("<!--", "").replace("-->", "");
                    }
                    String commentString = id;
                    String description = nav.getCell(row, 1);
                    if (!description.isEmpty()) {
                        commentString = commentString + "/" + description;
                    }
                    resources.addContent(new Comment(commentString));
                    row++;
                    continue;
                }
                try {
                    value = nav.getCell(row, col);
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    break;
                }
                if (value.isEmpty()) {
                    row++;
                    continue;
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
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
            row++;
        }
        try {
            final File file = new File(fileName);
            if (!file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            } else if (file.exists()) {
                file.delete();
            }

            // 4. 파일에 출력
            final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName));
            new XMLOutputter(Format.getPrettyFormat().setIndent("    ").setEncoding("UTF-8")).output(doc, writer);
            writer.close();
            //출처: http://devhome.tistory.com/74 [미주엘의 개발이야기]
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getText(String valueRaw) {
        String value = valueRaw;
        value = value.replace("\'", "\\\'");
        value = value.replace("\\\\\'", "\\\'");
        value = value.replace("\"", "\\\"");
        value = value.replace("\\\\\"", "\\\"");
        value = value.replace("\n", "\\n");
        return value;
    }

    private Element getStringArrayItem(String id, String value) {
        Element stringArray = new Element("String-array");
        stringArray.setAttribute("name", id.replace("[]", ""));
        String separator = "/n";
        if (value.contains("_x000a_")) separator = "_x000a_";
        String[] items = value.split(separator);
        for (String item : items) {
            Element child = new Element("item");
            child.setText(getText(item));
            stringArray.addContent(child);
        }
        return stringArray;
    }
}
