package stringtable;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidParameterException;

public class StringTableGenerator {

	public static void main(String[] args) throws Exception{
		
		if (args.length != 2)
		{
			throw new InvalidParameterException("<source xlsx> <res path>");
		}
		
		System.out.println("Generate string tables.");
		System.out.println("\tsource: " + args[0]);
		System.out.println("\tres: " + args[1]);

		File source = new File(args[0]);
		File pathRes = new File(args[1]);
		

		FileInputStream is = new FileInputStream(source);
		XSSFWorkbook workbook = new XSSFWorkbook(is);
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		Sheet2Strings.convert(sheet, pathRes);
		
		System.out.println("Completed.");

		is.close();
	}

}
