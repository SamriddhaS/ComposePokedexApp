package com.samriddha.composepokedexapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ComposePokedexApplication:Application() {

    //Initialise timeber debug tree
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}