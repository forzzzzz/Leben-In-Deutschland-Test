package com.marktkachenko.lebenindeutschland.models.deepLTranslate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Translate : TranslateRepository {

    override suspend fun translateText(texts: List<String>, targetLanguage: String): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                texts.map { text ->
                    val response = RetrofitInstance.api.translateText(TranslateRepository.API_KEY, text, targetLanguage)
                    response.translations[0].text
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}