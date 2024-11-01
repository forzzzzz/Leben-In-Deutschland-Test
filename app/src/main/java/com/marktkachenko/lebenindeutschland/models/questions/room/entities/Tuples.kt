package com.marktkachenko.lebenindeutschland.models.questions.room.entities

import androidx.room.ColumnInfo

data class IdTuple(
    val id: Long
)

data class TestTuple(
    val id: Long,
    val number: Int,
    val question: String,
    @ColumnInfo(name = "answer_1") val answer1: String,
    @ColumnInfo(name = "answer_2") val answer2: String,
    @ColumnInfo(name = "answer_3") val answer3: String,
    @ColumnInfo(name = "answer_4") val answer4: String,
    @ColumnInfo(name = "correct_answer") val correctAnswer: Int,
    val image: Int,
    val topic: Int,
    @ColumnInfo(name = "last_answer") val lastAnswer: Int,
    @ColumnInfo(name = "number_of_correct_answers") val numberOfCorrectAnswers: Int,
    @ColumnInfo(name = "number_of_incorrect_answers") val numberOfIncorrectAnswers: Int,
    val statistic: Int,
    @ColumnInfo(name = "is_favorite") val isFavorite: Int
)



data class QuestionUpdateStatisticTuple(
    val id: Long,
    @ColumnInfo(name = "last_answer") val lastAnswer: Int,
    @ColumnInfo(name = "number_of_correct_answers") val numberOfCorrectAnswers: Int,
    @ColumnInfo(name = "number_of_incorrect_answers") val numberOfIncorrectAnswers: Int,
    val statistic: Int,
)

data class QuestionUpdateIsFavoriteTuple(
    val id: Long,
    @ColumnInfo(name = "is_favorite") val isFavorite: Int
)