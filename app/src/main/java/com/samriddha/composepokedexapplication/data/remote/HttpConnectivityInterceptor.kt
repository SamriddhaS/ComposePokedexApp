package com.samriddha.composepokedexapplication.data.remote

import android.content.Context
import com.samriddha.composepokedexapplication.utils.CheckInternet
import com.samriddha.composepokedexapplication.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HttpConnectivityInterceptor(private val context:Context):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!CheckInternet.isOnline(context))
            throw NoInternetException("Internet Not Available")
        return chain.proceed(chain.request())
    }
}