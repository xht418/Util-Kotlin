package com.example.utlikotlin

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeHelper {
    fun nowInUtcMillis() = LocalDateTime.now().toUtcMillis()

    fun getNowString(dateTimeFormat: String) = nowInUtcMillis().toDateTimeString(dateTimeFormat)

    fun getTodayStartLong() = LocalDateTime.now().with(LocalTime.MIN).toUtcMillis()

    fun getTodayEndLong() = LocalDateTime.now().with(LocalTime.MAX).toUtcMillis()

    fun getFutureDaysStartLong(dayCount: Long) = LocalDateTime.now().plusDays(dayCount).with(LocalTime.MIN).toUtcMillis()

    fun getFutureDaysEndLong(dayCount: Long) = LocalDateTime.now().plusDays(dayCount).with(LocalTime.MAX).toUtcMillis()

    fun getPastDaysStartLong(dayCount: Long) = LocalDateTime.now().minusDays(dayCount).with(LocalTime.MIN).toUtcMillis()

    fun getPastDaysEndLong(dayCount: Long) = LocalDateTime.now().minusDays(dayCount).with(LocalTime.MAX).toUtcMillis()

    fun getDateTimeLong(localDate: LocalDate, localTime: LocalTime) = LocalDateTime.of(localDate, localTime).toUtcMillis()

    fun getTodayByTimeLong(hour: Int, minute: Int): Long {
        val formattedHour = hour.toDigit(2)
        val formattedMinute = minute.toDigit(2)

        return LocalDateTime.of(LocalDate.now(), LocalTime.parse("$formattedHour:$formattedMinute:00")).toUtcMillis()
    }

    fun getFutureDaysString(dateTimeFormat: String, dayCount: Long): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat)
        val targetLocalDateTime = LocalDateTime.now().plusDays(dayCount)

        return targetLocalDateTime.format(dateTimeFormatter)
    }

    fun getPastDaysString(dateTimeFormat: String, dayCount: Long): String {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat)
        val targetLocalDateTime = LocalDateTime.now().minusDays(dayCount)

        return targetLocalDateTime.format(dateTimeFormatter)
    }
}