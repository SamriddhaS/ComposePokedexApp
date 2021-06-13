package com.samriddha.composepokedexapplication.di

import android.content.Context
import com.samriddha.composepokedexapplication.data.remote.HttpConnectivityInterceptor
import com.samriddha.composepokedexapplication.data.remote.HttpRequestInterceptor
import com.samriddha.composepokedexapplication.data.remote.PokeApi
import com.samriddha.composepokedexapplication.data.repository.PokemonRepository
import com.samriddha.composepokedexapplication.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /* Ok http client provider.Add your interceptors here ex: logging interceptor,request interceptor
    * ,connectivity interceptor etc.*/
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(6000, TimeUnit.SECONDS)
            .readTimeout(600, TimeUnit.SECONDS)
            .addInterceptor(HttpRequestInterceptor())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(HttpConnectivityInterceptor(context))
            .writeTimeout(600, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePokeApi(retrofit: Retrofit):PokeApi {
        return retrofit.create(PokeApi::class.java)
    }

}