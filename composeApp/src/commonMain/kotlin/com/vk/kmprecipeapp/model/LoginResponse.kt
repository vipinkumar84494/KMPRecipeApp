package com.vk.kmprecipeapp.model

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponse(
    val accessToken: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val gender: String? = null,
    val id: String? = null,
    val image: String? = null,
    val lastName: String? = null,
    val refreshToken: String? = null,
    val username: String? = null,
    val message: String? = null,
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int
)