package com.rsupport.stringtable

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

object StringTableGenerator {

    @Throws(Exception::class)
    fun generate(
        source: String,
        resPath: String,
        targetSheetName: String,
        rowPositionColumnHeader: Int = 1,
        xmlFileName: String = "strings_generated.xml",
        doNotConvertNewLine: Boolean = false,
        defaultLanguageForValues: String = "en",
    ) {
        println("Generate string tables.")
        println("\tsource: $source")
        println("\tres: $resPath")
        val sourceFile = File(source)
        val pathRes = File(resPath)
        val inputStream = FileInputStream(sourceFile)
        val workbook = XSSFWorkbook(inputStream)
        Sheet2Strings.convert(
            sheet = getTargetSheet(targetSheetName, workbook),
            pathRes = pathRes,
            rowPositionColumnHeader = rowPositionColumnHeader,
            defaultLanguageForValues = defaultLanguageForValues,
            outputXmlFileName = xmlFileName,
            doNotConvertNewLine = doNotConvertNewLine,
        )
        println("Completed.")
        inputStream.close()
    }

    private fun getTargetSheet(targetSheetName: String?, workbook: XSSFWorkbook): XSSFSheet {
        if (targetSheetName.isNullOrEmpty())
            return workbook.getSheetAt(0)

        return workbook.getSheet(targetSheetName).also { result ->
            assert(result != null) { "Not found target sheet: $targetSheetName" }
        }
    }
}