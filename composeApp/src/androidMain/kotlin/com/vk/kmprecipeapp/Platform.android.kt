package com.vk.kmprecipeapp

import com.vk.kmprecipeapp.network.NetworkModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp


fun createAndroidHttpClient(): HttpClient {
    return NetworkModule.createHttpClient(OkHttp)
}