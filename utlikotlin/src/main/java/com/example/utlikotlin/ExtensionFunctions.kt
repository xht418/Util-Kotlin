package com.example.utlikotlin

fun Double.roundDecimal(digit: Int) = "%,.${digit}f".format(this)