package com.example.utlikotlin

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateTimeHelper {
    fun getNowLong() = LocalDateTime.now().toLong()

    fun getNowString(dateTimeFormat: String) = getNowLong().toDateTimeString(dateTimeFormat)

    fun getTodayStartLong() = LocalDateTime.now().with(LocalTime.MIN).toLong()

    fun getTodayEndLong() = LocalDateTime.now().with(LocalTime.MAX).toLong()

    fun getFutureDaysStartLong(dayCount: Long) = LocalDateTime.now().plusDays(dayCount).with(LocalTime.MIN).toLong()

    fun getFutureDaysEndLong(dayCount: Long) = LocalDateTime.now().plusDays(dayCount).with(LocalTime.MAX).toLong()

    fun getPastDaysStartLong(dayCount: Long) = LocalDateTime.now().minusDays(dayCount).with(LocalTime.MIN).toLong()

    fun getPastDaysEndLong(dayCount: Long) = LocalDateTime.now().minusDays(dayCount).with(LocalTime.MAX).toLong()

    fun getTodayByTimeLong(hour: Int, minute: Int): Long {
        val formattedHour = hour.toDigit(2)
        val formattedMinute = minute.toDigit(2)

        return LocalDateTime.of(LocalDate.now(), LocalTime.parse("$formattedHour:$formattedMinute:00")).toLong()
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