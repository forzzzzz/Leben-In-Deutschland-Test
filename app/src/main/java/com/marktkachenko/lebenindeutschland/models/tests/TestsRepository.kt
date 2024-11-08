package com.marktkachenko.lebenindeutschland.models.tests

interface TestsRepository {

    suspend fun loadTests()

    fun getTests(): List<Test>

    fun updateLand()

    fun addListener(listener: TestsListener)

    fun removeListener(listener: TestsListener)
}