package com.vk.kmprecipeapp.di

import com.russhwolf.settings.Settings
import com.vk.kmprecipeapp.viewModel.ItemsViewModel
import com.vk.kmprecipeapp.data.ItemRepository
import com.vk.kmprecipeapp.data.ItemRepositoryImpl
import com.vk.kmprecipeapp.data.SettingsRepository
import com.vk.kmprecipeapp.network.ApiClient
import com.vk.kmprecipeapp.viewModel.SettingsViewModel
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module

import org.koin.dsl.module

fun initKoin(
    httpClient: HttpClient,
    platformModule: Module,
    settings: Settings,
) {
    val appModule = module {
        single { ApiClient(httpClient) }
        single<ItemRepository> { ItemRepositoryImpl(get()) }
        single<Settings> {  settings }
        single { SettingsRepository(get()) }
        single { SettingsViewModel() }
        factory { ItemsViewModel(get()) }
    }
    startKoin { modules(appModule) }
}
