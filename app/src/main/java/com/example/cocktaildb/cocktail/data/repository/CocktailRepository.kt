package com.example.cocktaildb.cocktail.data.repository

import com.example.cocktaildb.cocktail.data.datasource.CocktailDataSource
import com.example.cocktaildb.cocktail.data.model.Cocktail

interface ICocktailRepository {
    suspend fun getCocktails(): List<Cocktail>
    suspend fun getCocktailById(id: String): Cocktail?
}

class CocktailRepository(
    private val dataSource: CocktailDataSource
) : ICocktailRepository {

    override suspend fun getCocktails(): List<Cocktail> {
        return dataSource.getCocktails()
    }

    override suspend fun getCocktailById(id: String): Cocktail? {
        return dataSource.getCocktailById(id)
    }
}
