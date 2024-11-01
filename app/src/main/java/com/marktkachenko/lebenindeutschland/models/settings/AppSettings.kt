package com.marktkachenko.lebenindeutschland.models.settings

interface AppSettings {

    fun setThemeId(themeId: Int)

    fun setLandId(landId: Int)

    fun setTargetLanguageCode(languageCode: String)

    fun getThemeId(): Int

    fun getLandId(): Int

    fun getTargetLanguageCode(): String

    companion object{

        const val DEFAULT_THEME_ID = -1
        const val NO_LAND_ID = -1
        const val DEFAULT_TARGET_LANGUAGE_ID = "EN"
    }
}