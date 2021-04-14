package com.rsupport.stringtable

import org.apache.poi.xssf.usermodel.XSSFSheet
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
        val inputStream = FileInputStream(source)
        val workbook = XSSFWorkbook(inputStream)
        val targetSheetName = if (args.size > 2) args[2] else null
        val indexRowNum = args[2]

        Sheet2Strings.convert(getTargetSheet(targetSheetName, workbook), pathRes, indexRowNum.toInt())
        println("Completed.")
        inputStream.close()
    }

    private fun getTargetSheet(targetSheetName: String?, workbook: XSSFWorkbook): XSSFSheet {
        if (targetSheetName == null)
            return workbook.getSheetAt(0)

        return workbook.getSheet(targetSheetName).also { result ->
            assert(result != null) { "Not found target sheet: $targetSheetName" }
        }
    }
}