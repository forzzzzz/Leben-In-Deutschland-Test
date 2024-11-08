package com.marktkachenko.lebenindeutschland.models.questions

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.TranslateRepository
import com.marktkachenko.lebenindeutschland.models.questions.room.RoomQuestionsRepository
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.models.tests.TestFilter
import com.marktkachenko.lebenindeutschland.screens.test.TestActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicLong

typealias QuestionsListener = (questions: List<Question>) -> Unit

class QuestionsService(
    private val questionsRepository: RoomQuestionsRepository,
    private val context: Context,
    private val appSettings: AppSettings,
    private val translateRepository: TranslateRepository
) : QuestionsRepository {

    private var questions = mutableListOf<Question>()

    private val listeners = mutableListOf<QuestionsListener>()

    override suspend fun loadQuestions() {
        questions = createQuestions()
        notifyChanges()
    }

    override fun getQuestions(): List<Question> {
        return questions
    }

    override fun getCount(): Int {
        return questions.size
    }

    override fun updateQuestions(question: Question, selectedRadioButtonId: Int) {
        var isCheckedAnswer1 = false
        var isCheckedAnswer2 = false
        var isCheckedAnswer3 = false
        var isCheckedAnswer4 = false

        val defaultColor = context.getColor(android.R.color.transparent)
        val correctColor = context.getColor(R.color.correct_answer)
        val incorrectColor = context.getColor(R.color.incorrect_answer)

        var answer1Color = defaultColor
        var answer2Color = defaultColor
        var answer3Color = defaultColor
        var answer4Color = defaultColor


        if (selectedRadioButtonId == 1){
            isCheckedAnswer1 = true
        }
        if (selectedRadioButtonId == 2){
            isCheckedAnswer2 = true
        }
        if (selectedRadioButtonId == 3){
            isCheckedAnswer3 = true
        }
        if (selectedRadioButtonId == 4){
            isCheckedAnswer4 = true
        }

        val isCorrectAnswer = selectedRadioButtonId == question.correctAnswer


        if (isCorrectAnswer && selectedRadioButtonId == 1){
            answer1Color = correctColor
        } else if (!isCorrectAnswer && selectedRadioButtonId == 1){
            answer1Color = incorrectColor
        }
        if (isCorrectAnswer && selectedRadioButtonId == 2){
            answer2Color = correctColor
        } else if (!isCorrectAnswer && selectedRadioButtonId == 2){
            answer2Color = incorrectColor
        }
        if (isCorrectAnswer && selectedRadioButtonId == 3){
            answer3Color = correctColor
        } else if (!isCorrectAnswer && selectedRadioButtonId == 3){
            answer3Color = incorrectColor
        }
        if (isCorrectAnswer && selectedRadioButtonId == 4){
            answer4Color = correctColor
        } else if (!isCorrectAnswer && selectedRadioButtonId == 4){
            answer4Color = incorrectColor
        }

        if (!isCorrectAnswer){
            when (question.correctAnswer) {
                1 -> {
                    answer1Color = correctColor
                }
                2 -> {
                    answer2Color = correctColor
                }
                3 -> {
                    answer3Color = correctColor
                }
                4 -> {
                    answer4Color = correctColor
                }
            }
        }

        val updatedQuestion = questions[question.id.toInt()].copy(
            isCheckedAnswer1 = isCheckedAnswer1,
            isCheckedAnswer2 = isCheckedAnswer2,
            isCheckedAnswer3 = isCheckedAnswer3,
            isCheckedAnswer4 = isCheckedAnswer4,
            answer1Color = answer1Color,
            answer2Color = answer2Color,
            answer3Color = answer3Color,
            answer4Color = answer4Color,
            isCorrectAnswer = isCorrectAnswer
        )
        questions = ArrayList(questions)
        questions[question.id.toInt()] = updatedQuestion
        notifyChanges()

        var isCorrectAnswerNumber = -1
        var numberOfCorrectAnswers = question.correctAnswersNumber
        var numberOfIncorrectAnswers = question.incorrectAnswersNumber
        var statistic = 4

        if (isCorrectAnswer){
            isCorrectAnswerNumber = 1
            numberOfCorrectAnswers++
        }else {
            isCorrectAnswerNumber = 0
            numberOfIncorrectAnswers++
        }

        if (numberOfIncorrectAnswers > numberOfCorrectAnswers){
            statistic = Statistics.MOSTLY_WRONG.value
        }
        if (isCorrectAnswerNumber == 0){
            statistic = Statistics.LAST_ANSWER_WRONG.value
        }
        if (isCorrectAnswerNumber == -1){
            statistic = Statistics.NOT_ANSWERED_YET.value
        }
        if (isCorrectAnswerNumber == 1){
            statistic = Statistics.LAST_ANSWER_RIGHT.value
        }
        if (numberOfIncorrectAnswers < numberOfCorrectAnswers){
            statistic = Statistics.MOSTLY_RIGHT.value
        }


        updateDB(
            question.id,
            isCorrectAnswerNumber,
            numberOfCorrectAnswers,
            numberOfIncorrectAnswers,
            statistic
        )
    }

    override fun translate(question: Question, progressBar: ProgressBar) {
        val texts = listOf(
            question.question,
            question.answer1,
            question.answer2,
            question.answer3,
            question.answer4
        )


        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE

            val translatedTexts = withContext(Dispatchers.IO) {
                translateRepository.translateText(texts)
            }

            val updatedQuestion = questions[question.id.toInt()].copy(
                translatedQuestion = translatedTexts[0],
                translatedAnswer1 = translatedTexts[1],
                translatedAnswer2 = translatedTexts[2],
                translatedAnswer3 = translatedTexts[3],
                translatedAnswer4 = translatedTexts[4],
            )

            questions = ArrayList(questions)
            questions[question.id.toInt()] = updatedQuestion

            progressBar.visibility = View.GONE
            notifyChanges()
        }
    }

    override fun favorite(question: Question) {
        val isFavorite: Int = if (question.isFavorite == 0){
            1
        } else {
            0
        }

        GlobalScope.launch {
            questionsRepository.updateQuestionIsFavorite(question.id, isFavorite)
        }

        val updatedQuestion = questions[question.id.toInt()].copy(
            isFavorite = isFavorite
        )

        questions = ArrayList(questions)
        questions[question.id.toInt()] = updatedQuestion

        notifyChanges()
    }

    override fun addListener(listener: QuestionsListener) {
        listeners.add(listener)
        listener.invoke(questions)
    }

    override fun removeListener(listener: QuestionsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(questions) }
    }

    private fun updateDB(
        id: Long,
        lastAnswer: Int,
        numberOfCorrectAnswers: Int,
        numberOfIncorrectAnswers: Int,
        statistic: Int
    ){
        GlobalScope.launch(Dispatchers.IO) {
            questionsRepository.updateQuestionStatistic(id, lastAnswer, numberOfCorrectAnswers, numberOfIncorrectAnswers, statistic)
        }
    }

    private suspend fun createQuestions(): MutableList<Question> = withContext(Dispatchers.IO) {
        val idCounter = AtomicLong(0)
        val questions = mutableListOf<Question>()

        val questionsDatabase = when (TestActivity.testFilter) {
            TestFilter.THEME.id -> questionsRepository.getQuestionsByTheme(TestActivity.testFilterNumber)
            TestFilter.TOPIC.id -> questionsRepository.getQuestionsByTopic(TestActivity.testFilterNumber)
            TestFilter.LAND.id -> questionsRepository.getQuestionsByTopic(TestActivity.testFilterNumber)
            TestFilter.ALL.id -> questionsRepository.getQuestions()
            TestFilter.IMAGE.id -> questionsRepository.getQuestionsWithImages()
            TestFilter.STATISTIC.id -> questionsRepository.getQuestionsByStatistic(TestActivity.testFilterNumber)
            TestFilter.IS_FAVORITE.id -> questionsRepository.getQuestionsByIsFavorite()
            else -> questionsRepository.getQuestions()
        }

        val questionsDBList = questionsDatabase.firstOrNull() ?: emptyList()

        questionsDBList.forEach { question ->
            val topicNameId = Topics.entries.find { it.value == question.topic }?.nameId
                ?: Lands.entries.find { it.value == question.topic }?.nameId

            val topicString = topicNameId?.let { context.getString(it) } ?: "Unknown topic"

            questions.add(
                Question(
                    id = idCounter.getAndIncrement(),
                    question = question.question,
                    translatedQuestion = null,
                    answer1 = question.answer1,
                    translatedAnswer1 = null,
                    answer2 = question.answer2,
                    translatedAnswer2 = null,
                    answer3 = question.answer3,
                    translatedAnswer3 = null,
                    answer4 = question.answer4,
                    translatedAnswer4 = null,
                    correctAnswer = question.correctAnswer,
                    image = context.resources.getIdentifier(question.image, "drawable", context.packageName),
                    topic = topicString,
                    landId = question.topic,
                    questionNumber = question.number.toString(),
                    isCorrectAnswer = null,
                    isCheckedAnswer1 = false,
                    isCheckedAnswer2 = false,
                    isCheckedAnswer3 = false,
                    isCheckedAnswer4 = false,
                    answer1Color = context.getColor(android.R.color.transparent),
                    answer2Color = context.getColor(android.R.color.transparent),
                    answer3Color = context.getColor(android.R.color.transparent),
                    answer4Color = context.getColor(android.R.color.transparent),
                    correctAnswersNumber = question.numberOfCorrectAnswers,
                    incorrectAnswersNumber = question.numberOfIncorrectAnswers,
                    isFavorite = question.isFavorite
                )
            )
        }

        if (TestActivity.testFilter == 0) {
            val questionsWithDifferentTopic = questions.filter { it.landId != appSettings.getLandId() }
            val questionsWithTopic2 = questions.filter { it.landId == appSettings.getLandId() }

            val randomQuestionsWithDifferentTopic = questionsWithDifferentTopic.shuffled().take(30)
            val randomQuestionsWithTopic2 = questionsWithTopic2.shuffled().take(3)

            var selectedQuestions = (randomQuestionsWithDifferentTopic + randomQuestionsWithTopic2).shuffled()

            selectedQuestions = selectedQuestions.mapIndexed { index, question ->
                question.copy(id = index.toLong())
            }.toMutableList()

            return@withContext selectedQuestions.toMutableList()
        } else {
//            questions.sortBy { it.questionNumber.toInt() }
            return@withContext questions
        }
    }
}