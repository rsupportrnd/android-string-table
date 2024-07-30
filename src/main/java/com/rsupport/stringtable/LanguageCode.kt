package com.rsupport.stringtable

import java.util.*

class LanguageCode {
    companion object {
        fun isValid(languageCode: String): Boolean {
            val availableLanguageCodes = Locale.getAvailableLocales().map { it.toLanguageTag() }.toSet()

            if (languageCode.startsWith("values")) {
                if (languageCode == "values") return true
                val actualCode = languageCode.removePrefix("values-")
                return availableLanguageCodes.contains(actualCode)
            }

            return availableLanguageCodes.contains(languageCode)
        }
    }


}
