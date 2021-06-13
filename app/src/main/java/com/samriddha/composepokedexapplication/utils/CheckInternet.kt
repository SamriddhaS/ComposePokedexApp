package com.samriddha.composepokedexapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object CheckInternet {

    fun isNetworkAvailable(context: Context):Boolean{
        val connectivityManager =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }

    @Suppress("DEPRECATION")
    fun isOnline(context: Context):Boolean{

        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
                        return true
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        return true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        return true
                    } else if(type == ConnectivityManager.TYPE_VPN) {
                        return true
                    }
                }
            }
        }
        return false
    }

}