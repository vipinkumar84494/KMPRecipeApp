package com.vk.kmprecipeapp

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.vk.kmprecipeapp.viewModel.ItemsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val androidModule = module {
    viewModel { ItemsViewModel(get()) }
}

fun provideSettings(context: Context): Settings {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(prefs)
}



