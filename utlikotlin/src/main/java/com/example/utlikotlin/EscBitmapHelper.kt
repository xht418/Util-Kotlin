package com.example.utlikotlin

import android.graphics.Bitmap
import android.util.Log
import java.util.*

object EscBitmapHelper {
    private const val HEX_STRING = "0123456789ABCDEF"

    private val baseBinaries = arrayOf(
        "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"
    )

    fun getBytes(bmp: Bitmap): ByteArray? {
        val bWidth = bmp.width
        val bHeight = bmp.height
        val binaries = mutableListOf<String>()
        val zeroCount = bWidth % 8
        var emptyString = ""
        var buffer: StringBuffer

        if (zeroCount > 0) {
            for (i in 0 until 8 - zeroCount) {
                emptyString += "0"
            }
        }

        for (i in 0 until bHeight) {
            buffer = StringBuffer()

            for (j in 0 until bWidth) {
                val color = bmp.getPixel(j, i)
                val r = color shr 16 and 0xff
                val g = color shr 8 and 0xff
                val b = color and 0xff

                if (r > 160 && g > 160 && b > 160) {        // if color close to white，bit='0', else bit='1'
                    buffer.append("0")
                } else {
                    buffer.append("1")
                }
            }

            if (zeroCount > 0) {
                buffer.append(emptyString)
            }

            binaries.add(buffer.toString())
        }

        val bmpHexList = binaryListToHexStringList(binaries)
        val commandHexString = "1D763000"
        var widthHexString = Integer.toHexString(if (bWidth % 8 == 0) bWidth / 8 else bWidth / 8 + 1)

        if (widthHexString.length > 2) {
            Log.e("decodeBitmap error", " width is too large")

            return null
        } else if (widthHexString.length == 1) {
            widthHexString = "0$widthHexString"
        }

        widthHexString += "00"

        var heightHexString = Integer.toHexString(bHeight)

        if (heightHexString.length > 2) {
            Log.e("decodeBitmap error", " height is too large")

            return null
        } else if (heightHexString.length == 1) {
            heightHexString = "0$heightHexString"
        }

        heightHexString += "00"

        val commandList = mutableListOf<String>()

        commandList.add(commandHexString + widthHexString + heightHexString)
        commandList.addAll(bmpHexList)

        return hexListToBytes(commandList)
    }

    private fun binaryListToHexStringList(binaries: List<String>): List<String> {
        val hexList = mutableListOf<String>()

        for (binary in binaries) {
            val buffer = StringBuffer()
            var i = 0

            while (i < binary.length) {
                val str = binary.substring(i, i + 8)
                val hexString = myBinaryStrToHexString(str)

                buffer.append(hexString)

                i += 8
            }

            hexList.add(buffer.toString())
        }

        return hexList
    }

    private fun myBinaryStrToHexString(binaries: String): String {
        var hex = ""
        val f4 = binaries.substring(0, 4)
        val b4 = binaries.substring(4, 8)

        for (i in baseBinaries.indices) {
            if (f4 == baseBinaries[i]) {
                hex += HEX_STRING.substring(i, i + 1)
            }
        }

        for (i in baseBinaries.indices) {
            if (b4 == baseBinaries[i]) {
                hex += HEX_STRING.substring(i, i + 1)
            }
        }

        return hex
    }

    private fun hexListToBytes(list: List<String>): ByteArray {
        val commandList = mutableListOf<ByteArray?>()

        for (hexStr in list) {
            commandList.add(hexStringToBytes(hexStr))
        }

        return sysCopy(commandList)
    }

    private fun hexStringToBytes(hexString: String?): ByteArray? {
        var hexString2 = hexString

        if (hexString2 == null || hexString2 == "") {
            return null
        }

        hexString2 = hexString2.toUpperCase(Locale.ROOT)

        val length = hexString2.length / 2
        val hexChars = hexString2.toCharArray()
        val bytes = ByteArray(length)

        for (i in 0 until length) {
            val pos = i * 2

            bytes[i] = (charToByte(hexChars[pos]).toInt() shl 4 or charToByte(hexChars[pos + 1]).toInt()).toByte()
        }

        return bytes
    }

    private fun sysCopy(srcArrays: List<ByteArray?>): ByteArray {
        var len = 0

        for (srcArray in srcArrays) {
            len += srcArray!!.size
        }

        val destArray = ByteArray(len)
        var destLen = 0

        for (srcArray in srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray!!.size)

            destLen += srcArray.size
        }

        return destArray
    }

    private fun charToByte(c: Char): Byte {
        return "0123456789ABCDEF".indexOf(c).toByte()
    }
}