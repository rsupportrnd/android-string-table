package com.rsupport.stringtable

object Path {
    fun combine(pathString: String, childName: String): String {
        val pathDelimiter = getDelimiter(pathString + childName)
        var result = pathString
        result = if (pathString.endsWith(pathDelimiter)) pathString else pathString + pathDelimiter
        result = if (childName.startsWith(pathDelimiter)) result + childName.substring(1) else result + childName
        return result
    }

    private fun getDelimiter(path: String): String {
        return if (path.indexOf('\\') != -1) "\\" else "/"
    }

    fun combine(pathString: String, childName1: String, vararg filenames: String): String {
        var result = combine(pathString, childName1)
        for (filename in filenames) {
            result = combine(result, filename)
        }
        return result
    }
}