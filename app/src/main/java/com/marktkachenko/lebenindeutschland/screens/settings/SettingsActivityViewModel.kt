package com.marktkachenko.lebenindeutschland.screens.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marktkachenko.lebenindeutschland.models.androidInteraction.AndroidInteractionRepository
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.utils.share

class SettingsActivityViewModel(
    private val appSettings: AppSettings,
    private val androidInteractionRepository: AndroidInteractionRepository
) : ViewModel() {

    private val _themeId = MutableLiveData(appSettings.getThemeId())
    val themeId = _themeId.share()

    private val _targetLanguageCode = MutableLiveData(appSettings.getTargetLanguageCode())
    val targetLanguageCode = _targetLanguageCode.share()

    private val _version = MutableLiveData<String>()
    val version = _version.share()

    private val _showLandsDialogEvent = MutableLiveData<Boolean>()
    val showLandsDialogEvent = _showLandsDialogEvent.share()

    private val _showDeepLLanguageDialogEvent = MutableLiveData<Boolean>()
    val showDeepLLanguageDialogEvent = _showDeepLLanguageDialogEvent.share()



    fun setThemeId(themeId: Int) {
        _themeId.value = themeId
        appSettings.setThemeId(themeId)
    }

    fun setTargetLanguageCode(languageCode: String) {
        _targetLanguageCode.value = languageCode
        appSettings.setTargetLanguageCode(languageCode)
    }

    fun setVersion(version: String){
        _version.value = version
    }

    fun showLandsDialog(){
        _showLandsDialogEvent.value = true
    }

    fun onLandsDialogShown() {
        _showLandsDialogEvent.value = false
    }

    fun showDeepLLanguageDialog(){
        _showDeepLLanguageDialogEvent.value = true
    }

    fun onDeepLLanguageDialogShown() {
        _showDeepLLanguageDialogEvent.value = false
    }

    fun copyVersionToClipboard() {
        version.value?.let { androidInteractionRepository.copyToClipboard(it) }
    }

    fun openSourceCodeUrl(url: String) {
        androidInteractionRepository.openUrl(url)
    }
}