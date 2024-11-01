package com.marktkachenko.lebenindeutschland.models.questions.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.IdTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.QuestionDBEntity
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.TestTuple
import com.marktkachenko.lebenindeutschland.models.questions.room.entities.UpdateQuestionTuple


@Dao
interface QuestionsDao {

    @Query("SELECT id FROM questions WHERE theme = :theme")
    suspend fun findIdByTheme(theme: Int): IdTuple?

    @Query("SELECT id FROM questions WHERE topic = :topic")
    suspend fun findIdByTopic(topic: Int): IdTuple?

    @Query("SELECT id FROM questions WHERE is_favorite = :isFavorite")
    suspend fun findIdByIsFavorite(isFavorite: Int): IdTuple?

    @Query("SELECT id FROM questions WHERE statistic = :statistic")
    suspend fun finIdByStatistic(statistic: Int): IdTuple?

    @Query("SELECT id FROM questions")
    suspend fun selectId(): IdTuple?

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, last_answer, number_of_correct_answers, number_of_incorrect_answers, statistic, is_favorite FROM questions")
    suspend fun selectQuestions(): TestTuple?

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, last_answer, number_of_correct_answers, number_of_incorrect_answers, statistic, is_favorite FROM questions WHERE theme = :theme")
    suspend fun selectQuestionsByTheme(theme: Int): TestTuple?

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, last_answer, number_of_correct_answers, number_of_incorrect_answers, statistic, is_favorite FROM questions WHERE topic = :topic")
    suspend fun selectQuestionsByTopic(topic: Int): TestTuple?

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, last_answer, number_of_correct_answers, number_of_incorrect_answers, statistic, is_favorite FROM questions WHERE is_favorite = :isFavorite")
    suspend fun selectQuestionsByIsFavorite(isFavorite: Int): TestTuple?

    @Query("SELECT id, number, question, answer_1, answer_2, answer_3, answer_4, correct_answer, image, topic, last_answer, number_of_correct_answers, number_of_incorrect_answers, statistic, is_favorite FROM questions WHERE statistic = :statistic")
    suspend fun selectQuestionsByStatistic(statistic: Int): TestTuple?



    @Update(entity = QuestionDBEntity::class)
    suspend fun updateQuestion(updateQuestionTuple: UpdateQuestionTuple)
}