package com.hdev.food.utils.extensions

import android.content.res.Configuration
import androidx.fragment.app.FragmentActivity
import com.hdev.food.App.Companion.context
import java.util.*

/**
 * Created by Josaél Hernández on 4/20/20.
 * Contact : josaeljjh@gmail.com
 */

fun countryCodeToEmojiFlag(countryCode: String): String {
    return countryCode
        .toUpperCase(Locale.US)
        .map { char ->
            Character.codePointAt("$char", 0) - 0x41 + 0x1F1E6
        }
        .map { codePoint ->
            Character.toChars(codePoint)
        }
        .joinToString(separator = "") { charArray ->
            String(charArray)
        }
}

//obtener nombre del pais
fun getCountryCode(countryCode: String): String {
    return Locale("", countryCode).displayCountry.trim()
}

fun applyOverrideConfiguration() {
    val locale = Locale("es", "ES")
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.createConfigurationContext(config)
}