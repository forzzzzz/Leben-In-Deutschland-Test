package com.marktkachenko.lebenindeutschland.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marktkachenko.lebenindeutschland.models.questions.room.QuestionsDao
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionDBEntity

@Database(
    version = 1,
    entities = [
        QuestionDBEntity::class
    ]
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getQuestionsDao(): QuestionsDao
}