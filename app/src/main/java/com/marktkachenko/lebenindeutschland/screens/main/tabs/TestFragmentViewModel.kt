package com.marktkachenko.lebenindeutschland.screens.main.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.models.tests.Test
import com.marktkachenko.lebenindeutschland.models.tests.TestsListener
import com.marktkachenko.lebenindeutschland.models.tests.TestsRepository
import com.marktkachenko.lebenindeutschland.utils.share
import kotlinx.coroutines.launch

class TestFragmentViewModel(
    private val testsRepository: TestsRepository,
    private val appSettings: AppSettings
) : ViewModel() {

    private val _landId = MutableLiveData(appSettings.getLandId())
    val landId = _landId.share()

    private val _showLandsDialogEvent = MutableLiveData<Boolean>()
    val showLandsDialogEvent = _showLandsDialogEvent.share()

    private val _tests = MutableLiveData<List<Test>>()
    val tests = _tests.share()

    private val listener: TestsListener = {
        _tests.value = it
    }

    init {
        viewModelScope.launch {
            if (appSettings.getLandId() == AppSettings.NO_LAND_ID){
                showLandsDialog()
            }

            testsRepository.loadTests()
            testsRepository.addListener(listener)
        }
    }

    override fun onCleared() {
        super.onCleared()
        testsRepository.removeListener(listener)
    }

    fun setLandId(newLandId: Int) {
        _landId.value = newLandId
    }

    fun showLandsDialog() {
        _showLandsDialogEvent.value = true
    }

    fun onLandsDialogShown() {
        _showLandsDialogEvent.value = false
    }

    fun updateLandInTest(){
        testsRepository.updateLand()
    }
}