package com.example.cocktaildb.cocktail.data.datasource

import com.example.cocktaildb.cocktail.data.model.Cocktail
import kotlinx.coroutines.delay

interface CocktailDataSource {
    suspend fun getCocktails(): List<Cocktail>
    suspend fun getCocktailById(id: String): Cocktail?
}

class CocktailLocalDataSource : CocktailDataSource {

    companion object {
        private const val NETWORK_DELAY_MS = 1000L
        private const val CACHE_DELAY_MS = 500L
    }

    override suspend fun getCocktails(): List<Cocktail> {
        // Simulate network delay
        delay(NETWORK_DELAY_MS)
        return listOf(
            Cocktail(
                id = "1",
                name = "Mojito",
                description = "A refreshing Cuban cocktail with mint and lime",
                imageUrl = "https://example.com/mojito.jpg",
                ingredients = listOf("White rum", "Lime juice", "Sugar", "Mint leaves", "Soda water"),
                instructions = "Muddle mint with sugar and lime juice. Add rum and fill with ice. Top with soda water."
            ),
            Cocktail(
                id = "2",
                name = "Margarita",
                description = "A classic Mexican cocktail with tequila and lime",
                imageUrl = "https://example.com/margarita.jpg",
                ingredients = listOf("Tequila", "Triple sec", "Lime juice", "Salt"),
                instructions = "Shake tequila, triple sec, and lime juice with ice. Strain into salt-rimmed glass."
            ),
            Cocktail(
                id = "3",
                name = "Old Fashioned",
                description = "A timeless bourbon cocktail with bitters",
                imageUrl = "https://example.com/old-fashioned.jpg",
                ingredients = listOf("Bourbon", "Angostura bitters", "Sugar cube", "Orange peel"),
                instructions = "Muddle sugar with bitters. Add bourbon and ice. Garnish with orange peel."
            ),
            Cocktail(
                id = "4",
                name = "Negroni",
                description = "An Italian cocktail with gin, vermouth, and Campari",
                imageUrl = "https://example.com/negroni.jpg",
                ingredients = listOf("Gin", "Sweet vermouth", "Campari", "Orange peel"),
                instructions = "Stir gin, vermouth, and Campari with ice. Strain and garnish with orange peel."
            ),
            Cocktail(
                id = "5",
                name = "Daiquiri",
                description = "A classic rum cocktail with lime and sugar",
                imageUrl = "https://example.com/daiquiri.jpg",
                ingredients = listOf("White rum", "Lime juice", "Simple syrup"),
                instructions = "Shake rum, lime juice, and syrup with ice. Strain into chilled glass."
            )
        )
    }

    override suspend fun getCocktailById(id: String): Cocktail? {
        delay(CACHE_DELAY_MS)
        return getCocktails().find { it.id == id }
    }
}
