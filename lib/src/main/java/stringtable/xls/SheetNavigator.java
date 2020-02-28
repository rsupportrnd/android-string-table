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
	
	public boolean hasContents(int row, int col)
	{
		try {
			String cell = getCell(row, col);
			return cell.isEmpty() == false;
		} catch (NoSuchElementException e)
		{
			return false;
		}
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

	public String getCell(int row, int col, String defaultString) {
		try {
			String result = getCell(row, col);
			
			if (result.isEmpty())
				return defaultString;
			
			return result;
			
		} catch (Exception e) {
			return defaultString;
		}
	}

	public double getCellNumber(int row, int col, double defaultNumber) {
		Row rowData = sheet.getRow(row);
		
		if (rowData==null)
			return defaultNumber;
		
		Cell cell = rowData.getCell(col);

		if (cell == null)
			return defaultNumber;
		
		switch (cell.getCellType())
		{
		case XSSFCell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}
		
		return defaultNumber;
	}

}
