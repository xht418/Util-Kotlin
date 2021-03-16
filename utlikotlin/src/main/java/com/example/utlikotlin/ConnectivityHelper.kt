package com.example.utlikotlin

import android.content.Context
import android.net.NetworkCapabilities

object ConnectivityHelper {
    fun getIsWifiOn(context: Context) = with(context.getConnectivityManager()) {
        getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
}