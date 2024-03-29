package com.rsupport.stringtable

import com.rsupport.stringtable.xls.SheetNavigator
import org.apache.poi.ss.usermodel.Sheet
import org.jdom2.Comment
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import kotlin.NoSuchElementException

object Sheet2Strings {
    fun convert(
        sheet: Sheet,
        pathRes: File,
        rowPositionColumnHeader: Int?,
        outputXmlFileName: String?,
        doNotConvertNewLine: Boolean
    ) {
        val nav = SheetNavigator(sheet)
        val (columnStringId, rowStringId) = findIdCell(nav, rowPositionColumnHeader)

        var column = columnStringId

        val xmlFileName = if(outputXmlFileName.isNullOrEmpty()) {
            "strings_generated.xml"
        } else {
            "$outputXmlFileName.xml"
        }

        while (true) {
            column++
            try {
                val languageCode = nav.getCell(rowStringId, column)
                if ("values" !in languageCode) continue
                val filename = Path.combine(pathRes.path, languageCode, xmlFileName)
                createStringsXml(
                    filename,
                    nav,
                    columnStringId,
                    rowStringId + 1,
                    column,
                    doNotConvertNewLine = doNotConvertNewLine,
                )
            } catch (e: NoSuchElementException) {
                break
            }
        }
    }

    private fun findIdCell(nav: SheetNavigator, rowPositionColumnHeader: Int?): Pair<Int, Int> {
        var columnIndex = 0
        val rowIndex = if(rowPositionColumnHeader == null) {
            0
        } else {
            rowPositionColumnHeader - 1
        }

        if(rowIndex < 0) throw IllegalStateException("Row index number starts with 1.")

        while (true) {
            try {
                if (isIdColumn(nav.getCell(rowIndex, columnIndex).toLowerCase())) {
                    return Pair(columnIndex, rowIndex)
                } else {
                    columnIndex++
                }
            } catch (e: NoSuchElementException) {
                try {
                    columnIndex++
                    nav.getCell(rowIndex, columnIndex)
                    continue
                } catch (e: NoSuchElementException) {
                }
                assert(false) { "ID Column not found. usually include ['id', 'identification', ...]" }
            }
        }
    }

    private fun isIdColumn(columnHeaderName: String): Boolean {
        if (columnHeaderName.startsWith("id "))
            return true
        if (columnHeaderName.endsWith(" id"))
            return true
        if (columnHeaderName in listOf("id", "ids", "identification", "identifications"))
            return true

        return false
    }

    private fun createStringsXml(
        filename: String,
        nav: SheetNavigator,
        columnStringId: Int,
        rowStringId: Int,
        col: Int,
        doNotConvertNewLine: Boolean,
    ) {
        val doc = Document()
        val resources = Element("resources")
        doc.addContent(resources)
        resources.addContent(Comment("generator link: https://github.com/jobtools/android-string-table "))
        resources.addContent(Comment("자동 생성된 파일입니다. 이 xml 파일을 직접 수정하지 마세요!"))
        resources.addContent(Comment("This file is auto generated. DO NOT EDIT THIS XML FILE!"))
        var row = rowStringId
        while (true) {
            var id: String
            var value: String
            try {
                id = nav.getCell(row, columnStringId)
                id = id.trim { it <= ' ' }
                if (id.isEmpty()) {
                    row++
                    continue
                }

                // 주석처리
                if (id.startsWith("<") ||
                        id.startsWith("/") ||
                        id.startsWith("#")) {
                    if (id.startsWith("<!--")) {
                        id = id.replace("<!--", "").replace("-->".toRegex(), "")
                    }
                    var commentString = id
                    val description = nav.getCell(row, columnStringId + 1)
                    if (!description.isEmpty()) {
                        commentString = "$commentString / $description "
                    }
                    val comment = Comment(commentString.trim())
                    resources.addContent(comment)
                    row++
                    continue
                }
                value = try {
                    nav.getCell(row, col)
                } catch (e: NoSuchElementException) {
                    ""
                }
                if (value.isEmpty()) {
                    row++
                    continue
                }
            } catch (e: NoSuchElementException) {
                if (row >= nav.sheet.lastRowNum) {
                    break
                } else {
                    row++
                    continue
                }

            }

            when {
                id.contains("[]") -> {
                    val stringArray = getStringArrayItem(
                        id,
                        value,
                        doNotConvertNewLine = doNotConvertNewLine,
                    )
                    resources.addContent(stringArray)
                }
                id.endsWith(".append") -> {
                    val previousId = id.dropLast(".append".length)
                    val original = resources.children.first { it.getAttribute("name").value == previousId }

                    original.text = original.text + getText(value, doNotConvertNewLine = doNotConvertNewLine)
                }
                else -> {
                    val string = Element("string")
                    string.setAttribute("name", id)
                    string.text = getText(value, doNotConvertNewLine = doNotConvertNewLine)
                    resources.addContent(string)
                }
            }
            row++
        }
        try {
            val file = File(filename)
            if (file.parentFile.isDirectory == false) file.parentFile.mkdirs() else if (file.exists()) file.delete()

            // 4. 파일에 출력
            val writer = OutputStreamWriter(FileOutputStream(File(filename)),
                    "UTF-8")
            //FileWriter writer = new FileWriter(filename);
            XMLOutputter(Format.getPrettyFormat().setIndent("    ").setEncoding("UTF-8")).output(doc, writer)
            writer.close()
            //출처: http://devhome.tistory.com/74 [미주엘의 개발이야기]
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getText(valueRaw: String, doNotConvertNewLine: Boolean): String {
        var value = valueRaw
        value = value.replace("\'", "\\\'")
        value = value.replace("\\\\\'", "\\\'")
        value = value.replace("\"", "\\\"")
        value = value.replace("\\\\\"", "\\\"")
        if (doNotConvertNewLine == false)
            value = value.replace("\n", "\\n")
        return value
    }

    private fun getStringArrayItem(
        id: String,
        value: String,
        doNotConvertNewLine: Boolean,
    ): Element {
        val stringArray = Element("string-array")
        stringArray.setAttribute("name", id.replace("[]", ""))
        var separator = "\n"
        if (value.contains("_x000a_")) separator = "_x000a_"
        val items = value.split(separator.toRegex()).toTypedArray()
        for (item in items) {
            val child = Element("item")
            child.text = getText(item, doNotConvertNewLine = doNotConvertNewLine)
            stringArray.addContent(child)
        }
        return stringArray
    }
}