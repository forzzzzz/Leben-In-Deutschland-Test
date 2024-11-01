package com.marktkachenko.lebenindeutschland.models.deepLTranslate

import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Translate(
    private val appSettings: AppSettings
) : TranslateRepository {

    override suspend fun translateText(texts: List<String>): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                texts.map { text ->
                    val response = RetrofitInstance.api.translateText(TranslateRepository.API_KEY, text, appSettings.getTargetLanguageCode())
                    response.translations[0].text
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}