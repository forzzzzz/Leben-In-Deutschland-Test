package com.marktkachenko.lebenindeutschland.models.deepLTranslate

import com.marktkachenko.lebenindeutschland.BuildConfig

interface TranslateRepository {

    suspend fun translateText(texts: List<String>, targetLanguage: String): List<String>

    companion object{
        const val API_KEY = BuildConfig.DEEPL_API_KEY
    }
}