package com.marktkachenko.lebenindeutschland.models.questions

import com.marktkachenko.lebenindeutschland.models.questions.room.entities.IdTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.TestTuple
import kotlinx.coroutines.flow.Flow

interface QuestionsRepository {

    fun getIdQuestionsByTheme(theme: Int): List<IdTuple>?

    fun getIdQuestionsByTopic(topic: Int): List<IdTuple>?

    fun getIdQuestionsByIsFavorite(isFavorite: Int): List<IdTuple>?

    fun getIdQuestionsByStatistic(statistic: Int): List<IdTuple>?

    fun getIdQuestions(): List<IdTuple>?

    fun getQuestions(): Flow<List<TestTuple>?>

    fun getQuestionsByTheme(theme: Int): Flow<List<TestTuple>?>

    fun getQuestionsByTopic(topic: Int): Flow<List<TestTuple>?>

    fun getQuestionsByIsFavorite(isFavorite: Int): Flow<List<TestTuple>?>

    fun getQuestionsByStatistic(statistic: Int): Flow<List<TestTuple>?>


    suspend fun updateQuestionStatistic(
        id: Long,
        lastAnswer: Int,
        numberOfCorrectAnswers: Int,
        numberOfIncorrectAnswers: Int,
        statistic: Int
    )

    suspend fun updateQuestionIsFavorite(id: Long, isFavorite: Int)
}