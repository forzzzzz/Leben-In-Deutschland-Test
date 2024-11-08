package com.marktkachenko.lebenindeutschland.models.statistics

import android.content.Context
import androidx.core.content.ContextCompat
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.models.questions.room.RoomQuestionsRepository
import com.marktkachenko.lebenindeutschland.models.questions.Statistics
import com.marktkachenko.lebenindeutschland.models.tests.TestFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicLong

typealias StatisticsListener = (tests: List<Statistic>) -> Unit

class StatisticsService(
    private val roomQuestionsRepository: RoomQuestionsRepository,
    private val context: Context
) : StatisticsRepository {

    private var statistics = mutableListOf<Statistic>()

    private val listeners = mutableListOf<StatisticsListener>()

    override suspend fun loadStatistics() {
        statistics = createStatistics()
        notifyChanges()
    }

    override fun getStatistics(): List<Statistic> {
        return statistics
    }

    override fun addListener(listener: StatisticsListener) {
        listeners.add(listener)
        listener.invoke(statistics)
    }

    override fun removeListener(listener: StatisticsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(statistics) }
    }

    private suspend fun createStatistics(): MutableList<Statistic> = withContext(Dispatchers.IO) {
        val idCounter = AtomicLong(0)
        val tests = mutableListOf<Statistic>()

        Statistics.entries.forEach { statistic ->
            val statisticQuestions = roomQuestionsRepository.getIdQuestionsByStatistic(statistic.value)
                .firstOrNull() ?: emptyList()
            val statisticQuestionsCount = statisticQuestions.size.toString()

            tests.add(
                Statistic(
                    id = idCounter.getAndIncrement(),
                    title = ContextCompat.getString(context, statistic.titleId),
                    subtitle = ContextCompat.getString(context, statistic.subTitleId),
                    icon = R.drawable.baseline_query_stats_24,
                    decorLine = statistic.decorLine,
                    numberOfQuestions = statisticQuestionsCount,
                    testFilter = TestFilter.STATISTIC.id,
                    testFilterNumber = statistic.value
                )
            )
        }

        tests
    }
}