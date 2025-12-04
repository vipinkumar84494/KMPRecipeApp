package com.vk.kmprecipeapp.androidfcmservice

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.vk.kmprecipeapp.fcmservice.FcmShared
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MyFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val fcmService: AndroidFcmService by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FcmShared.onAndroidToken(token)
        fcmService.notifyNewToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }
}