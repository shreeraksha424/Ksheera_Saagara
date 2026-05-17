package com.ksheerasagara.app.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {
    fun format(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        return format.format(amount)
    }
}
