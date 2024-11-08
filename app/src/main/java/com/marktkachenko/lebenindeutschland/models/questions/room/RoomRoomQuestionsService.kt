package com.marktkachenko.lebenindeutschland.models.questions.room

import com.marktkachenko.lebenindeutschland.models.questions.Topics
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.IdTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionUpdateIsFavoriteTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionUpdateStatisticTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.TestTuple
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.utils.AsyncLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class RoomRoomQuestionsService(
    private val questionsDao: QuestionsDao,
    private val appSettings: AppSettings
) : RoomQuestionsRepository {

    private val currentLandIdFlow = AsyncLoader {
        MutableStateFlow(LandId(appSettings.getLandId()))
    }


    override suspend fun getIdQuestionsByIsFavorite(): Flow<List<IdTuple>?> {
        return questionsDao.findIdByIsFavorite()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getIdQuestionsByStatistic(statistic: Int): Flow<List<IdTuple>> {
        return currentLandIdFlow.get()
            .map { landId ->
                questionsDao.finIdByStatistic(statistic, landId.value, Topics.entries.first().value, Topics.entries.last().value)
            }
            .flattenMerge()
            .flowOn(Dispatchers.IO)
    }


    override suspend fun getIdQuestions(): Flow<List<IdTuple>> {
        return currentLandIdFlow.get()
            .map { landId ->
                questionsDao.selectId(landId.value, Topics.entries.first().value, Topics.entries.last().value)
            }
            .flattenMerge()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getQuestions(): Flow<List<TestTuple>> {
        return currentLandIdFlow.get()
            .map { landId ->
                questionsDao.selectQuestions(landId.value, Topics.entries.first().value, Topics.entries.last().value)
            }
            .flattenMerge()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getQuestionsByTheme(theme: Int): Flow<List<TestTuple>> {
        return questionsDao.selectQuestionsByTheme(theme)
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getQuestionsByTopic(topic: Int): Flow<List<TestTuple>> {
        return questionsDao.selectQuestionsByTopic(topic)
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getQuestionsWithImages(): Flow<List<TestTuple>> {
        return currentLandIdFlow.get()
            .map { landId ->
                questionsDao.selectQuestionsWithImages(landId.value, Topics.entries.first().value, Topics.entries.last().value)
            }
            .flattenMerge()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getQuestionsByIsFavorite(): Flow<List<TestTuple>> {
        return questionsDao.selectQuestionsByIsFavorite()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getQuestionsByStatistic(statistic: Int): Flow<List<TestTuple>> {
        return currentLandIdFlow.get()
            .map { landId ->
                questionsDao.selectQuestionsByStatistic(statistic, landId.value, Topics.entries.first().value, Topics.entries.last().value)
            }
            .flattenMerge()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun updateQuestionStatistic(
        id: Long,
        lastAnswer: Int,
        numberOfCorrectAnswers: Int,
        numberOfIncorrectAnswers: Int,
        statistic: Int
    ) {
        questionsDao.updateStatistic(QuestionUpdateStatisticTuple(
            id,
            lastAnswer,
            numberOfCorrectAnswers,
            numberOfIncorrectAnswers,
            statistic
        ))
    }

    override suspend fun updateQuestionIsFavorite(id: Long, isFavorite: Int) {
        questionsDao.updateIsFavorite(QuestionUpdateIsFavoriteTuple(id, isFavorite))
    }

    private class LandId(val value: Int)
}