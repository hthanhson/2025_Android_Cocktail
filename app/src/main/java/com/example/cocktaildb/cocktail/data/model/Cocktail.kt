package com.example.cocktaildb.cocktail.data.model

data class Cocktail(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val instructions: String
)
