package com.vk.kmprecipeapp.viewModel

import com.vk.kmprecipeapp.data.SettingsRepository
import com.vk.kmprecipeapp.model.LoginResponse
import com.vk.kmprecipeapp.utils.Cons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

//class SettingsViewModel: KoinComponent {
//    val repo: SettingsRepository by inject()
//
//}

class SettingsViewModel : KoinComponent {

    val repo: SettingsRepository by inject()
    private val _profilePic =
        MutableStateFlow(repo.getValue(Cons.PROFILE_PIC))

    val profilePic: StateFlow<String> = _profilePic

    fun updateProfilePic(newPic: String) {
        repo.saveValue(Cons.PROFILE_PIC, newPic)
        _profilePic.value = newPic
    }

    private val _userData =
        MutableStateFlow<LoginResponse?>(repo.getUserData(Cons.USER_DATA))

    val userData: StateFlow<LoginResponse?> = _userData

    fun updateUserData(user: LoginResponse) {
        repo.saveUserData(Cons.USER_DATA, user)
        _userData.value = user
    }

    fun clearData() {
        repo.clear()
        _profilePic.value = ""
        _userData.value = null
    }
}
