package com.marktkachenko.lebenindeutschland.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import kotlinx.coroutines.launch
import com.marktkachenko.lebenindeutschland.utils.share

class MainActivityViewModel(
    private val appSettings: AppSettings
) : ViewModel() {

    private val _landId = MutableLiveData(appSettings.getLandId())
    val landId = _landId.share()

    private val _showLandsDialogEvent = MutableLiveData<Boolean>()
    val showLandsDialogEvent = _showLandsDialogEvent.share()

    init {
        viewModelScope.launch {
            if (landId.value == AppSettings.NO_LAND_ID){
                _showLandsDialogEvent.value = true
            }
        }
    }

    fun onLandsDialogShown() {
        _showLandsDialogEvent.value = false
    }

    fun setLand(landId: Int) {
        _landId.value = landId
        appSettings.setLandId(landId)
    }
}