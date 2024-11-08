package com.marktkachenko.lebenindeutschland.models.questions.room

import com.marktkachenko.lebenindeutschland.models.questions.room.entities.IdTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.TestTuple
import kotlinx.coroutines.flow.Flow

interface RoomQuestionsRepository {

    suspend fun getIdQuestionsByIsFavorite(): Flow<List<IdTuple>?>

    suspend fun getIdQuestionsByStatistic(statistic: Int): Flow<List<IdTuple>>

    suspend fun getIdQuestions(): Flow<List<IdTuple>>

    suspend fun getQuestions(): Flow<List<TestTuple>>

    suspend fun getQuestionsByTheme(theme: Int): Flow<List<TestTuple>>

    suspend fun getQuestionsByTopic(topic: Int): Flow<List<TestTuple>>

    suspend fun getQuestionsWithImages(): Flow<List<TestTuple>>

    suspend fun getQuestionsByIsFavorite(): Flow<List<TestTuple>>

    suspend fun getQuestionsByStatistic(statistic: Int): Flow<List<TestTuple>>


    suspend fun updateQuestionStatistic(
        id: Long,
        lastAnswer: Int,
        numberOfCorrectAnswers: Int,
        numberOfIncorrectAnswers: Int,
        statistic: Int
    )

    suspend fun updateQuestionIsFavorite(id: Long, isFavorite: Int)
}