package stringtable

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.security.InvalidParameterException

object StringTableGenerator {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size < 2) {
            throw InvalidParameterException("<source xlsx> <res path> <sheet name>")
        }
        println("Generate string tables.")
        println("\tsource: " + args[0])
        println("\tres: " + args[1])
        val source = File(args[0])
        val pathRes = File(args[1])
        var sheetIndex = 0
        val inputStream = FileInputStream(source)
        val workbook = XSSFWorkbook(inputStream)
        if (args.size == 3) {
            val findIndex = workbook.getSheetIndex(args[2])
            if (findIndex >= 0) {
                sheetIndex = findIndex
            }
        }
        val sheet = workbook.getSheetAt(sheetIndex)
        Sheet2Strings.convert(sheet, pathRes)
        println("Completed.")
        inputStream.close()
    }
}