package com.rsupport.stringtable.xls

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFCell
import java.util.*

class SheetNavigator(var sheet: Sheet) {
    @kotlin.jvm.Throws(NoSuchElementException::class)
    fun getCell(row: Int, col: Int): String {
        val rowData = sheet.getRow(row)
                ?: throw NoSuchElementException("row is null (" + row + ", " + col + ") on " + sheet.sheetName)
        val cell = rowData.getCell(col)
                ?: throw NoSuchElementException("Cell is null (" + row + ", " + col + ") on " + sheet.sheetName)
        return getCellString(cell)
    }

    companion object {
        private fun getCellString(cell: Cell): String {
            when (cell.cellType) {
                CellType.NUMERIC -> return "" + cell.numericCellValue
                CellType.STRING -> return cell.stringCellValue.replace("_x000a_", "\n")
            }
            return ""
        }
    }
}