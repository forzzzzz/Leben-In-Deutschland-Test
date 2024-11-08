package com.marktkachenko.lebenindeutschland.screens.test

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.marktkachenko.lebenindeutschland.BaseActivity
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.databinding.ActivityTestBinding
import com.marktkachenko.lebenindeutschland.models.questions.Lands
import com.marktkachenko.lebenindeutschland.models.questions.Question
import com.marktkachenko.lebenindeutschland.models.questions.QuestionActionListener
import com.marktkachenko.lebenindeutschland.models.questions.QuestionsAdapter
import com.marktkachenko.lebenindeutschland.models.questions.Statistics
import com.marktkachenko.lebenindeutschland.models.questions.Themes
import com.marktkachenko.lebenindeutschland.models.questions.Topics
import com.marktkachenko.lebenindeutschland.models.tests.TestFilter
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class TestActivity : BaseActivity() {

    private lateinit var binding: ActivityTestBinding
    private lateinit var adapter: QuestionsAdapter

    private val timerViewModel: TimerViewModel by viewModels()
    private val viewModel by viewModelCreator { TestViewModel(Repositories.questionsRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = QuestionsAdapter(object : QuestionActionListener {
            override fun onClickAnswer1(question: Question) {
                viewModel.updateQuestion(question, 1)
            }

            override fun onClickAnswer2(question: Question) {
                viewModel.updateQuestion(question, 2)
            }

            override fun onClickAnswer3(question: Question) {
                viewModel.updateQuestion(question, 3)
            }

            override fun onClickAnswer4(question: Question) {
                viewModel.updateQuestion(question, 4)
            }

            override fun onTranslateClick(question: Question) {
                viewModel.translateQuestion(question, binding.translateProgressBar)
            }

            override fun onFavoriteClick(question: Question) {
                viewModel.setFavoriteQuestion(question)
            }
        })

        viewModel.questions.observe(this) {
            adapter.questions = it
        }

        val layoutManager = LinearLayoutManager(this)
        binding.testRecyclerView.layoutManager = layoutManager
        binding.testRecyclerView.adapter = adapter

        val title = when (testFilter) {
            TestFilter.THEME.id ->  getString(Themes.entries.find { it.value == testFilterNumber }!!.nameId)
            TestFilter.TOPIC.id -> getString(Topics.entries.find { it.value == testFilterNumber }!!.nameId)
            TestFilter.ALL.id -> getString(R.string.all_questions_title)
            TestFilter.LAND.id -> getString(Lands.entries.find { it.value == testFilterNumber }!!.nameId)
            TestFilter.IMAGE.id -> getString(R.string.image_questions_title)
            TestFilter.IS_FAVORITE.id -> getString(R.string.is_favorite_questions_title)
            TestFilter.STATISTIC.id -> getString(Statistics.entries.find { it.value == testFilterNumber }!!.titleId)
            else -> getString(R.string.main_test_cardView_title)
        }

        binding.topAppBar.title = title



        timerViewModel.timeLeft.observe(this) {
            binding.timer.text = it
        }

        viewModel.answered.observe(this) {
            binding.answered.text = it
        }

        viewModel.right.observe(this) {
            binding.right.text = it
        }

        binding.topAppBar.setNavigationOnClickListener{
            finish()
        }
    }

    companion object{
        var testFilter = 0
        var testFilterNumber = 0
    }
}