package com.example.cocktaildb.cocktail.data.model

import com.google.gson.annotations.SerializedName

data class CocktailApiResponse(
    @SerializedName("drinks")
    val drinks: List<CocktailApiModel>?
)

data class CocktailApiModel(
    @SerializedName("idDrink")
    val idDrink: String,
    @SerializedName("strDrink")
    val strDrink: String,
    @SerializedName("strCategory")
    val strCategory: String?,
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String?,
    @SerializedName("strInstructions")
    val strInstructions: String?,
    @SerializedName("strIngredient1")
    val strIngredient1: String?,
    @SerializedName("strIngredient2")
    val strIngredient2: String?,
    @SerializedName("strIngredient3")
    val strIngredient3: String?,
    @SerializedName("strIngredient4")
    val strIngredient4: String?,
    @SerializedName("strIngredient5")
    val strIngredient5: String?,
    @SerializedName("strMeasure1")
    val strMeasure1: String?,
    @SerializedName("strMeasure2")
    val strMeasure2: String?,
    @SerializedName("strMeasure3")
    val strMeasure3: String?,
    @SerializedName("strMeasure4")
    val strMeasure4: String?,
    @SerializedName("strMeasure5")
    val strMeasure5: String?
) {
    fun toCocktail(): Cocktail {
        val ingredientsWithMeasures = buildIngredientsWithMeasures()

        return Cocktail(
            id = idDrink,
            name = strDrink,
            description = strCategory ?: "Cocktail",
            imageUrl = strDrinkThumb ?: "",
            ingredients = ingredientsWithMeasures,
            instructions = strInstructions ?: ""
        )
    }

    private fun buildIngredientsWithMeasures(): List<String> {
        val ingredients = mutableListOf<String>()
        val measures = mutableListOf<String>()

        addIngredientIfValid(ingredients, measures, strIngredient1, strMeasure1)
        addIngredientIfValid(ingredients, measures, strIngredient2, strMeasure2)
        addIngredientIfValid(ingredients, measures, strIngredient3, strMeasure3)
        addIngredientIfValid(ingredients, measures, strIngredient4, strMeasure4)
        addIngredientIfValid(ingredients, measures, strIngredient5, strMeasure5)

        return ingredients.zip(measures) { ingredient, measure ->
            if (measure.isNotBlank()) "$ingredient ($measure)" else ingredient
        }
    }

    private fun addIngredientIfValid(
        ingredients: MutableList<String>,
        measures: MutableList<String>,
        ingredient: String?,
        measure: String?
    ) {
        if (!ingredient.isNullOrBlank()) {
            ingredients.add(ingredient)
            measures.add(measure ?: "")
        }
    }
}
