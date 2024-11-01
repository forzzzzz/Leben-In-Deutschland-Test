package com.marktkachenko.lebenindeutschland.models.questions.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marktkachenko.lebenindeutschland.models.questions.entities.Question

@Entity(
    tableName = "questions"
)
data class QuestionDBEntity(
    @PrimaryKey val id: Long,
    val number: Int,
    val question: String,
    @ColumnInfo(name = "answer_1") val answer1: String,
    @ColumnInfo(name = "answer_2") val answer2: String,
    @ColumnInfo(name = "answer_3") val answer3: String,
    @ColumnInfo(name = "answer_4") val answer4: String,
    @ColumnInfo(name = "correct_answer") val correctAnswer: Int,
    val image: Int,
    val theme: Int,
    val topic: Int,
    @ColumnInfo(name = "last_answer", defaultValue = "-1") val lastAnswer: Int,
    @ColumnInfo(name = "number_of_correct_answers", defaultValue = "0") val numberOfCorrectAnswers: Int,
    @ColumnInfo(name = "number_of_incorrect_answers", defaultValue = "0") val numberOfIncorrectAnswers: Int,
    @ColumnInfo(defaultValue = "4") val statistic: Int,
    @ColumnInfo(name = "is_favorite", defaultValue = "0") val isFavorite: Int
) {

    fun toQuestion(): Question = Question(
        id = id,
        number = number,
        question = question,
        answer1 = answer1,
        answer2 = answer2,
        answer3 = answer3,
        answer4 = answer4,
        correctAnswer = correctAnswer,
        image = image,
        theme = theme,
        topic = topic,
        lastAnswer = lastAnswer,
        numberOfCorrectAnswers = numberOfCorrectAnswers,
        numberOfIncorrectAnswers = numberOfIncorrectAnswers,
        statistic = statistic,
        isFavorite = isFavorite
    )

    companion object {
        fun fromQuestion(question: Question): Question = Question (
            id = 0,
            number = question.number,
            question = question.question,
            answer1 = question.answer1,
            answer2 = question.answer2,
            answer3 = question.answer3,
            answer4 = question.answer4,
            correctAnswer = question.correctAnswer,
            image = question.image,
            theme = question.theme,
            topic = question.topic,
            lastAnswer = -1,
            numberOfCorrectAnswers = 0,
            numberOfIncorrectAnswers = 0,
            statistic = 4,
            isFavorite = 0
        )
    }
}