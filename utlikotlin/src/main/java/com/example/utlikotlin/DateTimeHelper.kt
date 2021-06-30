package com.example.utlikotlin

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateTimeHelper {
    fun getNowLong() = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

    fun getNowString(dateTimeFormat: String) = getNowLong().toDateTimeString(dateTimeFormat)

    fun getTodayStartLong() = LocalDateTime.now().with(LocalTime.MIN).toEpochSecond(ZoneOffset.UTC)

    fun getTodayEndLong() = LocalDateTime.now().with(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC)

    fun getFutureDaysStartLong(dayCount: Long) = LocalDateTime.now().plusDays(dayCount).with(LocalTime.MIN).toEpochSecond(ZoneOffset.UTC)

    fun getFutureDaysEndLong(dayCount: Long) = LocalDateTime.now().plusDays(dayCount).with(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC)

    fun getPastDaysStartLong(dayCount: Long) = LocalDateTime.now().minusDays(dayCount).with(LocalTime.MIN).toEpochSecond(ZoneOffset.UTC)

    fun getPastDaysEndLong(dayCount: Long) = LocalDateTime.now().minusDays(dayCount).with(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC)

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