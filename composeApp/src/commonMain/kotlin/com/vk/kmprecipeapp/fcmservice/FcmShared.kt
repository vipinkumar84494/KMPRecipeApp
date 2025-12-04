package com.vk.kmprecipeapp.fcmservice

object FcmShared {
    private var tokenCallback: ((String) -> Unit)? = null

    fun registerTokenCallback(cb: (String) -> Unit) {
        tokenCallback = cb
    }

    fun onIosToken(token: String) {
        tokenCallback?.invoke(token)
    }

    fun onAndroidToken(token: String) {
        tokenCallback?.invoke(token)
    }
}