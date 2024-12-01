package com.semin.memo.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

val Long.formattedDateTime get() = run {
    val instant = Instant.fromEpochMilliseconds(this)
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = instant.toLocalDateTime(timeZone)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\nHH:mm:ss", Locale.getDefault())
    val formattedDateTime = localDateTime.toJavaLocalDateTime().format(formatter)
    formattedDateTime
}
