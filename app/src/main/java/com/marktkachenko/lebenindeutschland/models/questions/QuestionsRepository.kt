package com.marktkachenko.lebenindeutschland.models.questions

import android.widget.ProgressBar

interface QuestionsRepository {

    suspend fun loadQuestions()

    fun getQuestions(): List<Question>

    fun getCount(): Int

    fun updateQuestions(question: Question, selectedRadioButtonId: Int)

    fun translate(question: Question, progressBar: ProgressBar)

    fun favorite(question: Question)

    fun addListener(listener: QuestionsListener)

    fun removeListener(listener: QuestionsListener)
}