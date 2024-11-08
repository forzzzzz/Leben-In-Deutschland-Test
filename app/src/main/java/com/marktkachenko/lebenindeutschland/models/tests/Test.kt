package com.marktkachenko.lebenindeutschland.models.tests

import com.marktkachenko.lebenindeutschland.R

data class Test(
    val id: Long,
    val title: String,
    val subTitle: String,
    val icon: Int,
    val numberOfQuestions: String,
    val testFilter: Int,
    val testFilterNumber: Int,
    val section: String
)

enum class Sections(val nameId: Int){
    THEME(R.string.theme_chip),
    TOPIC(R.string.topic_chip),
    OTHERS(R.string.others_chip)
}

enum class TestFilter(val id: Int){
    THEME(1),
    TOPIC(2),
    ALL(3),
    LAND(4),
    IMAGE(5),
    IS_FAVORITE(6),
    STATISTIC(7)
}