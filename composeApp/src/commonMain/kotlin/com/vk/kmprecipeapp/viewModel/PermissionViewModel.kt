package com.vk.kmprecipeapp.viewModel

import com.vk.kmprecipeapp.utils.NotificationPermissionHandler

class PermissionViewModel(
     val handler: NotificationPermissionHandler
) {
    suspend fun askNotificationPermission(): Boolean {
        return handler.requestPermission()
    }
}
