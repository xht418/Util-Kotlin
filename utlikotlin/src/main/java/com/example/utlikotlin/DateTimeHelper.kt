package com.example.utlikotlin

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object DateTimeHelper {
    fun nowInSystemMillis() = LocalDateTime.now().toSystemMillis()

    fun nowInFormattedString(format: String) = nowInSystemMillis().toFormattedString(format)

    fun todayStartInSystemMillis() = LocalDateTime.now().with(LocalTime.MIN).toSystemMillis()

    fun todayEndInSystemMillis() = LocalDateTime.now().with(LocalTime.MAX).toSystemMillis()

    fun futureDayStartInSystemMillis(dayCount: Long) = LocalDateTime.now().plusDays(dayCount).with(LocalTime.MIN).toSystemMillis()

    fun futureDayEndInSystemMillis(dayCount: Long) = LocalDateTime.now().plusDays(dayCount).with(LocalTime.MAX).toSystemMillis()

    fun pastDayStartInSystemMillis(dayCount: Long) = LocalDateTime.now().minusDays(dayCount).with(LocalTime.MIN).toSystemMillis()

    fun pastDayEndInSystemMillis(dayCount: Long) = LocalDateTime.now().minusDays(dayCount).with(LocalTime.MAX).toSystemMillis()
}