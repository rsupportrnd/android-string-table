package com.rsupport.stringtable

import java.util.*

class LanguageCode {
    companion object {
        fun isValid(languageCode: String): Boolean {
            val availableLanguageCodes = Locale
                .getAvailableLocales()
                .map { it.toLanguageTag() }
                .toSet()

            val containsRegionQualifier = languageCode.contains("-r")
            val normalizedLanguageCode = if (containsRegionQualifier) languageCode.replace("-r", "-") else languageCode

            return normalizedLanguageCode in availableLanguageCodes
        }

        fun getActualCode(languageCode: String): String? {
            if (languageCode == "values") return null
            val code = languageCode.removePrefix("values-")
            return code.ifBlank { null }
        }

        // Android resource qualifier expects "-r<REGION>" for the region segment; a plain hyphen
        // ("zh-CN") is rejected by the resource merger at assembleDebug time. Normalize common
        // BCP 47-style codes to the Android form; pass through already-qualified codes and
        // language-only codes unchanged.
        fun toAndroidQualifier(code: String): String {
            val parts = code.split("-")
            if (parts.size < 2) return code
            if (parts.size == 2 && parts[1].length == 3 && parts[1].startsWith("r")) return code
            if (parts.size == 2 && parts[1].length == 2) return "${parts[0]}-r${parts[1]}"
            return code
        }
    }


}
