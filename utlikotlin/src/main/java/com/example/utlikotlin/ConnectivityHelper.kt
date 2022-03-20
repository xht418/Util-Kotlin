package com.example.utlikotlin

import android.content.Context
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import java.net.InetAddress
import java.nio.ByteBuffer
import java.nio.ByteOrder

object ConnectivityHelper {
    fun isWifiOn(context: Context) = with(context.getConnectivityManager()) {
        getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }

    fun isDataOn(context: Context) = with(context.getConnectivityManager()) {
        getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    }

    fun isNetworkAvailable(context: Context) = isWifiOn(context) || isDataOn(context)

    fun getIpAddress(context: Context): String {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        return wifiManager.connectionInfo.ipAddress.let {
            InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(it).array()).hostAddress
        }
    }
}