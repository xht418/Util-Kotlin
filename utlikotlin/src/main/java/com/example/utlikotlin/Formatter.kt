package com.example.utlikotlin

object Formatter {
    @JvmStatic
    fun getRoundedDecimal(number: Double, digit: Int) = "%,.${digit}f".format(number)
}