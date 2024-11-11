package com.marktkachenko.lebenindeutschland

import android.content.Context
import androidx.room.Room
import com.marktkachenko.lebenindeutschland.models.androidInteraction.AndroidInteraction
import com.marktkachenko.lebenindeutschland.models.androidInteraction.AndroidInteractionRepository
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.Translate
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.TranslateRepository
import com.marktkachenko.lebenindeutschland.models.questions.QuestionsRepository
import com.marktkachenko.lebenindeutschland.models.questions.QuestionsService
import com.marktkachenko.lebenindeutschland.models.questions.room.RoomQuestionsRepository
import com.marktkachenko.lebenindeutschland.models.questions.room.RoomQuestionsService
import com.marktkachenko.lebenindeutschland.models.room.AppDataBase
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.models.settings.Preferences
import com.marktkachenko.lebenindeutschland.models.statistics.StatisticsRepository
import com.marktkachenko.lebenindeutschland.models.statistics.StatisticsService
import com.marktkachenko.lebenindeutschland.models.tests.TestsRepository
import com.marktkachenko.lebenindeutschland.models.tests.TestsService

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDataBase by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .build()
    }

    private val roomQuestionsRepository: RoomQuestionsRepository by lazy {
        RoomQuestionsService(database.getQuestionsDao(), appSettings)
    }

    val appSettings: AppSettings by lazy {
        Preferences(applicationContext)
    }

    val androidInteractionRepository: AndroidInteractionRepository by lazy {
        AndroidInteraction(applicationContext)
    }

    private val translateRepository: TranslateRepository by lazy {
        Translate(appSettings)
    }

    val testsRepository: TestsRepository by lazy {
        TestsService(roomQuestionsRepository, appSettings, applicationContext)
    }

    val statisticsRepository: StatisticsRepository by lazy {
        StatisticsService(roomQuestionsRepository, applicationContext)
    }

    val questionsRepository: QuestionsRepository by lazy {
        QuestionsService(roomQuestionsRepository, applicationContext, appSettings, translateRepository)
    }

    fun init(context: Context) {
        applicationContext = context
    }
}