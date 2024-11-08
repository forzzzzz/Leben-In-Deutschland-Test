package com.marktkachenko.lebenindeutschland.models.tests

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.models.questions.Lands
import com.marktkachenko.lebenindeutschland.models.questions.room.RoomQuestionsRepository
import com.marktkachenko.lebenindeutschland.models.questions.Themes
import com.marktkachenko.lebenindeutschland.models.questions.Topics
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicLong

typealias TestsListener = (tests: List<Test>) -> Unit

class TestsService(
    private val roomQuestionsRepository: RoomQuestionsRepository,
    private val appSettings: AppSettings,
    private val context: Context
) : TestsRepository {

    private var tests = mutableListOf<Test>()

    private val listeners = mutableListOf<TestsListener>()

    override suspend fun loadTests() {
        tests = createTests()
        notifyChanges()
    }

    override fun getTests(): List<Test> {
        return tests
    }

    override fun updateLand() {
        val landTest = tests.find { it.testFilter == TestFilter.LAND.id }
        if (landTest != null) {
            val landId = appSettings.getLandId()

            val landTitle = if (landId != AppSettings.NO_LAND_ID){
                Lands.entries.find { it.value == landId }!!.nameId
            }else{
                R.string.error_land_questions_title
            }

            val landNumberOfQuestions = Lands.entries.find { it.value == landId }?.numberOfQuestions?.toString() ?: "0"

            val updatedTest = tests[landTest.id.toInt()].copy(
                testFilterNumber = landId,
                numberOfQuestions = landNumberOfQuestions,
                title = getString(context, landTitle),
            )
            tests = ArrayList(tests)
            tests[landTest.id.toInt()] = updatedTest
        }
        notifyChanges()
    }

    override fun addListener(listener: TestsListener) {
        listeners.add(listener)
        listener.invoke(tests)
    }

    override fun removeListener(listener: TestsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(tests) }
    }

    private suspend fun createTests(): MutableList<Test> = withContext(Dispatchers.IO) {
        val idCounter = AtomicLong(0)
        val tests = mutableListOf<Test>()

        tests.add(
            Test(
                id = idCounter.getAndIncrement(),
                title = getString(context, R.string.all_questions_title),
                subTitle = getString(context, R.string.all_questions_subtitle),
                icon = R.drawable.baseline_fact_check_24,
                numberOfQuestions = "310",
                testFilter = TestFilter.ALL.id,
                testFilterNumber = 0,
                section = getString(context, Sections.OTHERS.nameId)
            )
        )

        val landTitle = if (appSettings.getLandId() != AppSettings.NO_LAND_ID){
            Lands.entries.find { it.value == appSettings.getLandId() }!!.nameId
        }else{
            R.string.error_land_questions_title
        }

        val landNumberOfQuestions = Lands.entries.find { it.value == appSettings.getLandId() }?.numberOfQuestions?.toString() ?: "0"

        tests.add(
            Test(
                id = idCounter.getAndIncrement(),
                title = getString(context, landTitle),
                subTitle = getString(context, R.string.land_questions_subtitle),
                icon = R.drawable.baseline_public_24,
                numberOfQuestions = landNumberOfQuestions,
                testFilter = TestFilter.LAND.id,
                testFilterNumber = appSettings.getLandId(),
                section = getString(context, Sections.OTHERS.nameId)
            )
        )

        tests.add(
            Test(
                id = idCounter.getAndIncrement(),
                title = getString(context, R.string.image_questions_title),
                subTitle = getString(context, R.string.image_questions_subtitle),
                icon = R.drawable.baseline_image_24,
                numberOfQuestions = "13",
                testFilter = TestFilter.IMAGE.id,
                testFilterNumber = 0,
                section = getString(context, Sections.OTHERS.nameId)
            )
        )

        val favoriteQuestions = roomQuestionsRepository.getIdQuestionsByIsFavorite()
            .firstOrNull() ?: emptyList()
        val favoriteQuestionsCount = favoriteQuestions.size

        tests.add(
            Test(
                id = idCounter.getAndIncrement(),
                title = getString(context, R.string.is_favorite_questions_title),
                subTitle = getString(context, R.string.is_favorite_questions_subtitle),
                icon = R.drawable.baseline_favorite_24,
                numberOfQuestions = favoriteQuestionsCount.toString(),
                testFilter = TestFilter.IS_FAVORITE.id,
                testFilterNumber = appSettings.getLandId(),
                section = getString(context, Sections.OTHERS.nameId)
            )
        )

        Themes.entries.forEach { theme ->
            tests.add(
                Test(
                    id = idCounter.getAndIncrement(),
                    title = getString(context, theme.nameId),
                    subTitle = getString(context, theme.nameId),
                    icon = theme.icon,
                    numberOfQuestions = theme.numberOfQuestions.toString(),
                    testFilter = TestFilter.THEME.id,
                    testFilterNumber = theme.value,
                    section = getString(context, Sections.THEME.nameId)
                )
            )
        }

        Topics.entries.forEach { topic ->
            tests.add(
                Test(
                    id = idCounter.getAndIncrement(),
                    title = getString(context, topic.nameId),
                    subTitle = getString(context, topic.theme.nameId),
                    icon = topic.theme.icon,
                    numberOfQuestions = topic.numberOfQuestions.toString(),
                    testFilter = TestFilter.TOPIC.id,
                    testFilterNumber = topic.value,
                    section = getString(context, Sections.TOPIC.nameId)
                )
            )
        }

        tests
    }
}