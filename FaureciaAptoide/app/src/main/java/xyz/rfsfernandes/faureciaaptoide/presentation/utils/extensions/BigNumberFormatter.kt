package xyz.rfsfernandes.faureciaaptoide.presentation.utils.extensions

import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

/**
 * Formats a big number into a more readable one
 *
 * @return A string formatted using the kMBTPE convention
 */
fun Int?.toSmallNumber(): String {
    val count = this
    if (count != null) {
        if (count < 1000) return "" + count
    }
    val exp = (ln(count?.toDouble() ?: 0.0) / ln(1000.0)).toInt()
    val format = DecimalFormat("0.#")
    val value = format.format(count?.div(1000.0.pow(exp.toDouble())) ?: 0.0)
    return String.format("%s%c", value, "kMBTPE"[exp - 1])
}
