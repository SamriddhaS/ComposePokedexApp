package com.samriddha.composepokedexapplication.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HttpRequestInterceptor : Interceptor {

  /*We can add any header (ex: authorization header/AuthId etc) here if that header is needed
  * before every network call.
  * ############# Authorization interceptor ######################
  * */
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    val request = originalRequest.newBuilder().url(originalRequest.url).build()
    Timber.d(request.toString())
    return chain.proceed(request)
  }
}
