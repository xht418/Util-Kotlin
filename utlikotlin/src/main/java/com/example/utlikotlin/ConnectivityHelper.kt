package com.example.utlikotlin

import android.content.Context
import android.net.NetworkCapabilities

object ConnectivityHelper {
    fun isWifiOn(context: Context) = with(context.getConnectivityManager()) {
        getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }

    fun isDataOn(context: Context) = with(context.getConnectivityManager()) {
        getNetworkCapabilities(activeNetwork)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    }

    fun isNetworkAvailable(context: Context) = isWifiOn(context) || isDataOn(context)

    fun getIpAddress(context: Context) = with(context.getConnectivityManager()) {
        getLinkProperties(activeNetwork)!!.linkAddresses[1].address.hostAddress!!
    }
}