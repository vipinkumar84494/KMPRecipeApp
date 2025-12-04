package com.vk.kmprecipeapp

import android.app.Application
import com.vk.kmprecipeapp.di.initKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val client = createAndroidHttpClient()
        val settings = provideSettings(this)


        initKoin(
            client,
            platformModule = androidModule,
            settings,
        )
    }
}