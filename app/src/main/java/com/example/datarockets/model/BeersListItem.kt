package com.example.datarockets.model

data class BeersListItem(
    val abv: Double,
    val description: String,
    val ibu: Double,
    val id: Int,
    val image_url: String,
    val ingredients: Ingredients,
    val name: String,
    val tagline: String,
)