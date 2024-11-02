package com.marktkachenko.lebenindeutschland.models.questions.entities

data class Question(
    val id: Long,
    val number: Int,
    val question: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: Int,
    val image: String,
    val theme: Int,
    val topic: Int,
    val lastAnswer: Int,
    val numberOfCorrectAnswers: Int,
    val numberOfIncorrectAnswers: Int,
    val statistic: Int,
    val isFavorite: Int
)
