package com.vk.kmprecipeapp



import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults


val iosModule = module {

}

fun provideSettings(): Settings {
    return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults())
}



