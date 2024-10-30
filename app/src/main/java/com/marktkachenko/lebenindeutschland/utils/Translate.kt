package com.marktkachenko.lebenindeutschland.utils

import com.marktkachenko.lebenindeutschland.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Translate {

    private const val API_KEY = BuildConfig.DEEPL_API_KEY

    suspend fun translateText(texts: List<String>, targetLanguage: String): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                texts.map { text ->
                    val response = RetrofitInstance.api.translateText(API_KEY, text, targetLanguage)
                    response.translations[0].text
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}