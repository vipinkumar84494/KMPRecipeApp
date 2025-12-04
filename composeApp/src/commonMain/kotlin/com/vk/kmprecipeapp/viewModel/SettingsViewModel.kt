package com.vk.kmprecipeapp.viewModel

import com.vk.kmprecipeapp.data.SettingsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class SettingsViewModel: KoinComponent {
    val repo: SettingsRepository by inject()

}