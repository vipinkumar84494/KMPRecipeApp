package com.vk.kmprecipeapp.androidfcmservice

import com.google.firebase.messaging.FirebaseMessaging
import com.vk.kmprecipeapp.fcmservice.FcmService
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AndroidFcmService: FcmService {

    private var callback: ((String) -> Unit)? = null

    override suspend fun getToken(): String?  = suspendCancellableCoroutine { continuation ->

        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                continuation.resume(token)
        }
            .addOnFailureListener { exception ->
                continuation.resume(null)
            }

    }

    override fun setOnNewToken(callback: (String) -> Unit) {
        this.callback = callback
    }

    fun notifyNewToken(token: String){
        callback?.invoke(token)
    }

}