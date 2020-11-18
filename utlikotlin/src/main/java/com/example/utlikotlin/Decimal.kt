package com.example.utlikotlin

object Decimal {
    @JvmStatic
    fun round(number: Double, digit: Int) = "%,.${digit}f".format(number)
}