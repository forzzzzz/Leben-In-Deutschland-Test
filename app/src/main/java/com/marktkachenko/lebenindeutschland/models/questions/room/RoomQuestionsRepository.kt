package com.marktkachenko.lebenindeutschland.models.questions.room

import android.util.Log
import com.marktkachenko.lebenindeutschland.models.questions.QuestionsRepository
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.IdTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionUpdateIsFavoriteTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionUpdateStatisticTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.TestTuple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class RoomQuestionsRepository(
    private val questionsDao: QuestionsDao,
) : QuestionsRepository {

    override fun getIdQuestionsByTheme(theme: Int): List<IdTuple>? {
        return try {
            questionsDao.findIdByTheme(theme)
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error fetching IDs by theme: ${e.message}", e)
            null
        }
    }

    override fun getIdQuestionsByTopic(topic: Int): List<IdTuple>? {
        return try {
            questionsDao.findIdByTopic(topic)
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error fetching IDs by topic: ${e.message}", e)
            null
        }
    }

    override fun getIdQuestionsWithImage(): List<IdTuple>? {
        return try {
            questionsDao.findIdWithImage()
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error fetching IDs with image: ${e.message}", e)
            null
        }
    }

    override fun getIdQuestionsByIsFavorite(isFavorite: Int): List<IdTuple>? {
        return try {
            questionsDao.findIdByIsFavorite(isFavorite)
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error fetching IDs by isFavorite: ${e.message}", e)
            null
        }
    }

    override fun getIdQuestionsByStatistic(statistic: Int): List<IdTuple>? {
        return try {
            questionsDao.finIdByStatistic(statistic)
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error fetching IDs by statistic: ${e.message}", e)
            null
        }
    }

    override fun getIdQuestions(): List<IdTuple>? {
        return try {
            questionsDao.selectId()
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error fetching IDs: ${e.message}", e)
            null
        }
    }

    override fun getQuestions(): Flow<List<TestTuple>?> {
        return questionsDao.selectQuestions()
            .catch { exception ->
                Log.e("DatabaseError", "Error fetching questions: ${exception.message}", exception)
                emit(emptyList())
            }
    }

    override fun getQuestionsByTheme(theme: Int): Flow<List<TestTuple>?> {
        return questionsDao.selectQuestionsByTheme(theme)
            .catch { exception ->
                Log.e("DatabaseError", "Error fetching questions by theme: ${exception.message}", exception)
                emit(emptyList())
            }
    }

    override fun getQuestionsByTopic(topic: Int): Flow<List<TestTuple>?> {
        return questionsDao.selectQuestionsByTopic(topic)
            .catch { exception ->
                Log.e("DatabaseError", "Error fetching questions by topic: ${exception.message}", exception)
                emit(emptyList())
            }
    }

    override fun getQuestionsByIsFavorite(isFavorite: Int): Flow<List<TestTuple>?> {
        return questionsDao.selectQuestionsByIsFavorite(isFavorite)
            .catch { exception ->
                Log.e("DatabaseError", "Error fetching questions by isFavorite: ${exception.message}", exception)
                emit(emptyList())
            }
    }

    override fun getQuestionsByStatistic(statistic: Int): Flow<List<TestTuple>?> {
        return questionsDao.selectQuestionsByStatistic(statistic)
            .catch { exception ->
                Log.e("DatabaseError", "Error fetching questions by statistic: ${exception.message}", exception)
                emit(emptyList())
            }
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
}