package com.marktkachenko.lebenindeutschland.models.questions.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.IdTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionDBEntity
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionUpdateIsFavoriteTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionUpdateStatisticTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.TestTuple
import kotlinx.coroutines.flow.Flow


@Dao
interface QuestionsDao {

    @Query("SELECT id FROM questions WHERE is_favorite = 1")
    fun findIdByIsFavorite(): Flow<List<IdTuple>?>

    @Query("""
    SELECT id
    FROM questions
    WHERE (topic = :targetLand OR topic BETWEEN :topicStart AND :topicEnd)
        AND statistic = :statistic
""")
    fun finIdByStatistic(statistic: Int, targetLand: Int, topicStart: Int, topicEnd: Int): Flow<List<IdTuple>>

    @Query("""
    SELECT id
    FROM questions
    WHERE topic = :targetLand
        OR topic BETWEEN :topicStart AND :topicEnd
""")
    fun selectId(targetLand: Int, topicStart: Int, topicEnd: Int): Flow<List<IdTuple>>

    @Query("""
    SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, last_answer, 
           number_of_correct_answers, number_of_incorrect_answers, statistic, is_favorite 
    FROM questions
    WHERE topic = :targetLand
        OR topic BETWEEN :topicStart AND :topicEnd
""")
    fun selectQuestions(targetLand: Int, topicStart: Int, topicEnd: Int): Flow<List<TestTuple>>

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, number_of_correct_answers, number_of_incorrect_answers, is_favorite FROM questions WHERE theme = :theme")
    fun selectQuestionsByTheme(theme: Int): Flow<List<TestTuple>>

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, number_of_correct_answers, number_of_incorrect_answers, is_favorite FROM questions WHERE topic = :topic")
    fun selectQuestionsByTopic(topic: Int): Flow<List<TestTuple>>

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, number_of_correct_answers, number_of_incorrect_answers, is_favorite FROM questions WHERE (topic = :targetLand OR topic BETWEEN :topicStart AND :topicEnd) AND image <> 0")
    fun selectQuestionsWithImages(targetLand: Int, topicStart: Int, topicEnd: Int): Flow<List<TestTuple>>

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, number_of_correct_answers, number_of_incorrect_answers, is_favorite FROM questions WHERE is_favorite = 1")
    fun selectQuestionsByIsFavorite(): Flow<List<TestTuple>>

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, number_of_correct_answers, number_of_incorrect_answers, is_favorite FROM questions WHERE (topic = :targetLand OR topic BETWEEN :topicStart AND :topicEnd) AND statistic = :statistic")
    fun selectQuestionsByStatistic(statistic: Int, targetLand: Int, topicStart: Int, topicEnd: Int): Flow<List<TestTuple>>


    @Update(entity = QuestionDBEntity::class)
    suspend fun updateStatistic(questionUpdateStatisticTuple: QuestionUpdateStatisticTuple)

    @Update(entity = QuestionDBEntity::class)
    suspend fun updateIsFavorite(questionUpdateIsFavoriteTuple: QuestionUpdateIsFavoriteTuple)
}