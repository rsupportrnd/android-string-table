package com.rsupport.stringtable;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StringTableGenerator {
    public static void generate(File source, String pathRes, String targetSheetName, int indexRowNum) throws IOException {
        System.out.println("Generate string tables.");
        System.out.println("\tsource: " + source.getPath());
        System.out.println("\tres: " + pathRes);
        File res = new File(pathRes);
        FileInputStream inputStream = new FileInputStream(source);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        Sheet2Strings.getInstance().convert(getTargetSheet(targetSheetName, workbook), res, indexRowNum);
        System.out.println("Completed.");
        inputStream.close();
    }

    private static XSSFSheet getTargetSheet(String targetSheetName, XSSFWorkbook workbook) {
        if (targetSheetName.isEmpty()) {
            return workbook.getSheetAt(0);
        }

        XSSFSheet resultSheet = workbook.getSheet(targetSheetName);
        assert (resultSheet != null) : "Not Found target sheet: " + targetSheetName;
        return resultSheet;
    }
}
