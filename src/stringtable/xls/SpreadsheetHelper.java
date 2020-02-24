package stringtable.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class SpreadsheetHelper {

	public static String getCellString(Cell cell) {
		if (cell == null)
			return "";
		
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		
		return cell.getStringCellValue().replace("_x000a_", "\n");
	}

	public static String getCellString(Cell cell, String defaultString) {
		String result = getCellString(cell);
		
		if (result.isEmpty())
			return defaultString;
		
		return result;
	}

}
