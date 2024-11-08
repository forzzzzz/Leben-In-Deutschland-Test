package com.marktkachenko.lebenindeutschland.models.statistics

data class Statistic(
    val id: Long,
    val title: String,
    val subtitle: String,
    val icon: Int,
    val decorLine: Int,
    var numberOfQuestions: String,
    val testFilter: Int,
    var testFilterNumber: Int
)
