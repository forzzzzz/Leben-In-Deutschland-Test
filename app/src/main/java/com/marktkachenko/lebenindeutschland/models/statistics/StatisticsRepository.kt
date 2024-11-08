package com.marktkachenko.lebenindeutschland.models.statistics

interface StatisticsRepository {

    suspend fun loadStatistics()

    fun getStatistics(): List<Statistic>

    fun addListener(listener: StatisticsListener)

    fun removeListener(listener: StatisticsListener)
}