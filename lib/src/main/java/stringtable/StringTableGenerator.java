package stringtable;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidParameterException;

public class StringTableGenerator {
	public static void main(String[] args) throws Exception{
		if (args.length < 2)
		{
			throw new InvalidParameterException("<source xlsx> <res path> <sheet name>");
		}
		System.out.println("Generate string tables.");
		System.out.println("\tsource: " + args[0]);
		System.out.println("\tres: " + args[1]);
		File source = new File(args[0]);
		File pathRes = new File(args[1]);
		int sheetIndex = 0;
		FileInputStream is = new FileInputStream(source);
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		if(args.length == 3){
			int findIndex = workbook.getSheetIndex(args[2]);
			if(findIndex >= 0){
				sheetIndex = findIndex;
			}
		}
		XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		Sheet2Strings.convert(sheet, pathRes);
		System.out.println("Completed.");
		is.close();
	}
}
