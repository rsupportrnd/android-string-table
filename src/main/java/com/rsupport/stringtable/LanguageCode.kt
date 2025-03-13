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
    }


}
