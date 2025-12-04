package com.vk.kmprecipeapp

import androidx.compose.ui.window.ComposeUIViewController
import com.vk.kmprecipeapp.di.initKoin
import com.vk.kmprecipeapp.ui.AppScreen
import com.vk.kmprecipeapp.utils.NotificationPermissionHandler
import com.vk.kmprecipeapp.viewModel.PermissionViewModel
import org.koin.compose.getKoin
import org.koin.dsl.module


fun MainViewController() = ComposeUIViewController()
{

    val settings = provideSettings()
    val handler = NotificationPermissionHandler()

    initKoin(
        httpClient = createIosHttpClient(),
        iosModule,
        settings,
    )

    getKoin().loadModules(
        listOf(
            module {
                single { handler }
                single { PermissionViewModel(get()) }
            }
        )
    )


    AppScreen()
}

