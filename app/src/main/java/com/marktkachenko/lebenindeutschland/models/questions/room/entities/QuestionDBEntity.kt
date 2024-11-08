package com.marktkachenko.lebenindeutschland.models.questions.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    indices = [
        Index("theme"),
        Index("id"),
        Index("topic"),
        Index("is_favorite")
    ]
)
data class QuestionDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val number: Int,
    val question: String,
    @ColumnInfo(name = "answer_1") val answer1: String,
    @ColumnInfo(name = "answer_2") val answer2: String,
    @ColumnInfo(name = "answer_3") val answer3: String,
    @ColumnInfo(name = "answer_4") val answer4: String,
    @ColumnInfo(name = "correct_answer") val correctAnswer: Int,
    @ColumnInfo(defaultValue = "0") val image: String,
    val theme: Int,
    val topic: Int,
    @ColumnInfo(name = "last_answer", defaultValue = "-1") val lastAnswer: Int,
    @ColumnInfo(name = "number_of_correct_answers", defaultValue = "0") val numberOfCorrectAnswers: Int,
    @ColumnInfo(name = "number_of_incorrect_answers", defaultValue = "0") val numberOfIncorrectAnswers: Int,
    @ColumnInfo(defaultValue = "4") val statistic: Int,
    @ColumnInfo(name = "is_favorite", defaultValue = "0") val isFavorite: Int
)