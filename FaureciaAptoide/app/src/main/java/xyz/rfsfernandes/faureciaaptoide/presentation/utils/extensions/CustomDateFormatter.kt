package xyz.rfsfernandes.faureciaaptoide.presentation.utils.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val dateRegex = Regex("^[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]{1,3})?\$")
val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

/**
 * Formats a string that matches a regex pattern into a usable date format
 */
fun String.formatDate(): String {
    return if (dateRegex.matches(this)) {
        val modifyToStandard = this.replace(" ", "T")
        val parsedDate = LocalDateTime.parse(modifyToStandard)
        dateTimeFormatter.format(parsedDate)
    } else {
        this
    }
}
