package com.marktkachenko.lebenindeutschland

import android.content.Context
import androidx.room.Room
import com.marktkachenko.lebenindeutschland.models.androidInteraction.AndroidInteraction
import com.marktkachenko.lebenindeutschland.models.androidInteraction.AndroidInteractionRepository
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.Translate
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.TranslateRepository
import com.marktkachenko.lebenindeutschland.models.questions.QuestionsRepository
import com.marktkachenko.lebenindeutschland.models.questions.room.RoomQuestionsRepository
import com.marktkachenko.lebenindeutschland.models.room.AppDataBase
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.models.settings.Preferences

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDataBase by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .build()
    }

    val questionsRepository: QuestionsRepository by lazy {
        RoomQuestionsRepository(database.getQuestionsDao())
    }

    val appSettings: AppSettings by lazy {
        Preferences(applicationContext)
    }

    val androidInteractionRepository: AndroidInteractionRepository by lazy {
        AndroidInteraction(applicationContext)
    }

    val translateRepository: TranslateRepository by lazy {
        Translate(appSettings)
    }

    fun init(context: Context) {
        applicationContext = context
    }
}