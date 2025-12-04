package com.vk.kmprecipeapp.utils

expect fun isValidEmail(email: String): Boolean

expect class NotificationPermissionHandler {
    suspend fun requestPermission(): Boolean
}

