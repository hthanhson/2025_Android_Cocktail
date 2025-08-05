package com.example.cocktaildb.cocktail.data.datasource

import com.example.cocktaildb.cocktail.data.model.CocktailApiResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CocktailRemoteDataSource : CocktailDataSource {

    companion object {
        private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
        private const val SEARCH_ENDPOINT = "search.php?f=a"
        private const val CONNECTION_TIMEOUT = 10000
        private const val READ_TIMEOUT = 10000
        private const val COCKTAILS_TO_SKIP = 7
        private const val COCKTAILS_TO_TAKE = 4
    }

    override suspend fun getCocktails(): List<com.example.cocktaildb.cocktail.data.model.Cocktail> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("$BASE_URL$SEARCH_ENDPOINT")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = CONNECTION_TIMEOUT
                connection.readTimeout = READ_TIMEOUT

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    val gson = Gson()
                    val apiResponse = gson.fromJson(response.toString(), CocktailApiResponse::class.java)

                    apiResponse.drinks?.drop(COCKTAILS_TO_SKIP)?.take(COCKTAILS_TO_TAKE)?.map { it.toCocktail() } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun getCocktailById(id: String): com.example.cocktaildb.cocktail.data.model.Cocktail? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("${BASE_URL}lookup.php?i=$id")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = CONNECTION_TIMEOUT
                connection.readTimeout = READ_TIMEOUT

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    val gson = Gson()
                    val apiResponse = gson.fromJson(response.toString(), CocktailApiResponse::class.java)

                    apiResponse.drinks?.firstOrNull()?.toCocktail()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
