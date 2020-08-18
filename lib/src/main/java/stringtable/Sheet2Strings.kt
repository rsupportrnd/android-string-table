package stringtable

import org.apache.poi.ss.usermodel.Sheet
import org.jdom2.Comment
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter
import stringtable.xls.SheetNavigator
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import kotlin.NoSuchElementException

object Sheet2Strings {
    fun convert(sheet: Sheet?, pathRes: File) {
        val nav = SheetNavigator(sheet)
        val (columnStringId, rowStringId) = findIdCell(nav)

        var column = columnStringId
        while (true) {
            column++
            try {
                val languageCode = nav.getCell(rowStringId, column)
                if ("values" !in languageCode) continue
                val filename = Path.combine(pathRes.path, languageCode, "strings_generated.xml")
                createStringsXml(filename, nav, columnStringId, rowStringId + 1, column)
            } catch (e: NoSuchElementException) {
                break
            }
        }
    }

    private fun findIdCell(nav: SheetNavigator): Pair<Int, Int> {
        var columnIndex = 0
        var rowIndex = 0

        while (true) {
            try {
                if (isIdColumn(nav.getCell(rowIndex, columnIndex).toLowerCase()))
                    return Pair(columnIndex, rowIndex)
            } catch (e: NoSuchElementException) {
                try {
                    nav.getCell(rowIndex + 1, 0)
                    rowIndex++
                    columnIndex = 0
                    continue
                } catch (e: NoSuchElementException) {
                }
                assert(false) { "ID Column not found. usually include ['id', 'identification', ...]" }
            }
            columnIndex++
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

    private fun createStringsXml(filename: String, nav: SheetNavigator, columnStringId: Int, rowStringId: Int, col: Int) {
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
                break
            }
            if (id.contains("[]")) {
                val stringArray = getStringArrayItem(id, value)
                resources.addContent(stringArray)
            } else {
                val string = Element("string")
                string.setAttribute("name", id)
                string.text = getText(value)
                resources.addContent(string)
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

    private fun getText(valueRaw: String): String {
        var value = valueRaw
        value = value.replace("\'", "\\\'")
        value = value.replace("\\\\\'", "\\\'")
        value = value.replace("\"", "\\\"")
        value = value.replace("\\\\\"", "\\\"")
        value = value.replace("\n", "\\n")
        return value
    }

    private fun getStringArrayItem(id: String, value: String): Element {
        val stringArray = Element("string-array")
        stringArray.setAttribute("name", id.replace("[]", ""))
        var separator = "\n"
        if (value.contains("_x000a_")) separator = "_x000a_"
        val items = value.split(separator.toRegex()).toTypedArray()
        for (item in items) {
            val child = Element("item")
            child.text = getText(item)
            stringArray.addContent(child)
        }
        return stringArray
    }
}