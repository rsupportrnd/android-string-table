package stringtable.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.util.NoSuchElementException;

public class SheetNavigator {

	public Sheet sheet;
	
	public SheetNavigator(Sheet masterSheet)
	{
		this.sheet = masterSheet;
	}


	public String getCell(int row, int col) throws NoSuchElementException
	{
		Row rowData = sheet.getRow(row);
		
		if (rowData==null)
			throw new NoSuchElementException("row is null (" + row + ", " + col + ") on "+ sheet.getSheetName());
		
		Cell cell = rowData.getCell(col);

		if (cell == null)
			throw new NoSuchElementException("Cell is null (" + row + ", " + col + ") on "+ sheet.getSheetName());
		
		return getCellString(cell);
	}
	
	private static String getCellString(Cell cell) {
		int cellType = cell.getCellType();
		
		switch (cellType)
		{
		case XSSFCell.CELL_TYPE_NUMERIC:
			return ""+cell.getNumericCellValue();
		case XSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue().replace("_x000a_", "\n");
		}
		return "";
	}

}
