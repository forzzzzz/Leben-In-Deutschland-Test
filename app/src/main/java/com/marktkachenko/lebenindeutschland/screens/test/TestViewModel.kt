package com.marktkachenko.lebenindeutschland.screens.test

import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marktkachenko.lebenindeutschland.models.questions.Question
import com.marktkachenko.lebenindeutschland.models.questions.QuestionsListener
import com.marktkachenko.lebenindeutschland.models.questions.QuestionsRepository
import com.marktkachenko.lebenindeutschland.utils.share
import kotlinx.coroutines.launch

class TestViewModel(
    private val questionsRepository: QuestionsRepository
) : ViewModel() {

    private var answeredCount = -1
    private var rightCount = -1

    private val _questions = MutableLiveData<List<Question>>()
    val questions = _questions.share()

    private val listener: QuestionsListener = {
        _questions.value = it
    }

    private val _answered = MutableLiveData<String>()
    val answered = _answered.share()

    private val _right = MutableLiveData<String>()
    val right = _right.share()

    init {
        viewModelScope.launch {
            questionsRepository.loadQuestions()
            questionsRepository.addListener(listener)

            updateAnswered()
            updateRight()
        }
    }

    override fun onCleared() {
        super.onCleared()
        questionsRepository.removeListener(listener)
    }

    fun updateQuestion(question: Question, selectedRadioButtonId: Int){
        questionsRepository.updateQuestions(question, selectedRadioButtonId)

        updateAnswered()
        if (question.correctAnswer == selectedRadioButtonId){
            updateRight()
        }
    }

    fun translateQuestion(question: Question, progressBar: ProgressBar) {
        questionsRepository.translate(question, progressBar)
    }

    fun setFavoriteQuestion(question: Question) {
        questionsRepository.favorite(question)
    }

    private fun updateAnswered() {
        val count = questionsRepository.getCount()
        answeredCount++
        val percentage = ((answeredCount.toDouble() / count) * 100).toInt()

        _answered.value = "$answeredCount/$count ($percentage%)"
    }

    private fun updateRight() {
        val count = questionsRepository.getCount()
        rightCount++
        val percentage = ((rightCount.toDouble() / count) * 100).toInt()

        _right.value = "$rightCount/$count ($percentage%)"
    }
}