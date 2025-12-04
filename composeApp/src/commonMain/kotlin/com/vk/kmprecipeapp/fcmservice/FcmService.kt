package com.vk.kmprecipeapp.fcmservice

interface FcmService {
    suspend fun getToken(): String?
    fun setOnNewToken(callback: (String) -> Unit)
}