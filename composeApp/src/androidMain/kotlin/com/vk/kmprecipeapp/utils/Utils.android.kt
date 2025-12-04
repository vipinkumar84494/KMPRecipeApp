package com.vk.kmprecipeapp.utils

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


actual fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

actual class NotificationPermissionHandler(
    private val activity: ComponentActivity
) {

    private var continuation: Continuation<Boolean>? = null

    private val launcher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            continuation?.resume(granted)
            continuation = null
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    actual suspend fun requestPermission(): Boolean =
        suspendCoroutine { cont ->
            continuation = cont
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
}

