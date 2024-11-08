package com.marktkachenko.lebenindeutschland.screens.main.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marktkachenko.lebenindeutschland.models.statistics.Statistic
import com.marktkachenko.lebenindeutschland.models.statistics.StatisticsListener
import com.marktkachenko.lebenindeutschland.models.statistics.StatisticsRepository
import com.marktkachenko.lebenindeutschland.utils.share
import kotlinx.coroutines.launch

class StatisticFragmentViewModel(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    private val _statistics = MutableLiveData<List<Statistic>>()
    val statistics = _statistics.share()

    private val listener: StatisticsListener = {
        _statistics.value = it
    }

    init {
        viewModelScope.launch {
            statisticsRepository.loadStatistics()
            statisticsRepository.addListener(listener)
        }
    }

    override fun onCleared() {
        super.onCleared()
        statisticsRepository.removeListener(listener)
    }
}