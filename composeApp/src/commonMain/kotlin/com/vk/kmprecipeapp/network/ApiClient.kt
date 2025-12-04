package com.vk.kmprecipeapp.network

import com.vk.kmprecipeapp.model.FoodDataResponse
import com.vk.kmprecipeapp.model.LoginRequest
import com.vk.kmprecipeapp.model.LoginResponse
import com.vk.kmprecipeapp.model.RecipesData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiClient(
    private val client: HttpClient,
) {

    private val baseUrl = "https://dummyjson.com/"
     private val baseUrl1 = "https://www.themealdb.com/api/json/v1/1/categories.php"
    suspend fun getFoodData(): FoodDataResponse {
        return client.get(baseUrl1).body<FoodDataResponse>()
    }

    suspend fun getRecipesData(): RecipesData {
        return client.get(baseUrl+"recipes").body<RecipesData>()
    }
    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return client.post(baseUrl+"auth/login"){
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body<LoginResponse>()

    }
}