package com.marktkachenko.lebenindeutschland

import android.content.Context
import com.marktkachenko.lebenindeutschland.models.androidInteraction.AndroidInteraction
import com.marktkachenko.lebenindeutschland.models.androidInteraction.AndroidInteractionRepository
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.Translate
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.TranslateRepository
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.models.settings.Preferences

object Repositories {

    private lateinit var applicationContext: Context

    val appSettings: AppSettings by lazy {
        Preferences(applicationContext)
    }

    val androidInteractionRepository: AndroidInteractionRepository by lazy {
        AndroidInteraction(applicationContext)
    }

    val translateRepository: TranslateRepository by lazy {
        Translate()
    }

    fun init(context: Context) {
        applicationContext = context
    }
}