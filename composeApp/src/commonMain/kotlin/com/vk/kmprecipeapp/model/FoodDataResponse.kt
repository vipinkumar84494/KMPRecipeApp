package com.vk.kmprecipeapp.model

import kotlinx.serialization.Serializable


@Serializable
data class FoodDataResponse(
    val categories: List<Category>
)

@Serializable
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)