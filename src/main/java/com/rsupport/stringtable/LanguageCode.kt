package com.rsupport.stringtable

import java.util.*

class LanguageCode {
    companion object {
        fun isValid(languageCode: String): Boolean {
            if (languageCode == "values") return true

            val code = if (languageCode.startsWith("values-")) languageCode.removePrefix("values-") else languageCode
            return getActualCode(code) != null
        }

        fun getActualCode(languageCode: String): String? {
            val availableLanguageCodes = Locale.getAvailableLocales().map { it.toLanguageTag() }.toSet()

            val actualCode = when {
                languageCode == "values" -> null
                languageCode.startsWith("values-") -> languageCode.removePrefix("values-")
                else -> languageCode
            }

            return if (actualCode in availableLanguageCodes) actualCode else null
        }
    }


}
