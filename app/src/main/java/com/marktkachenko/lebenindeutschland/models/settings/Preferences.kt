package com.marktkachenko.lebenindeutschland.models.settings

import android.content.Context
import android.content.SharedPreferences
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.DeepLLanguages
import java.util.Locale

class Preferences(
    context: Context
) : AppSettings {

    companion object {
        private const val KEY_THEME = "marktkachenko.lebenindeutschland.THEME"
        private const val KEY_LAND = "marktkachenko.lebenindeutschland.LAND"
        private const val KEY_TARGET_LANGUAGE = "marktkachenko.lebenindeutschland.TARGET_LANGUAGE"
    }

    private val preferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)



    override fun setThemeId(themeId: Int) {
        preferences.edit()
            .putInt(KEY_THEME, themeId)
            .apply()
    }

    override fun setLandId(landId: Int) {
        preferences.edit()
            .putInt(KEY_LAND, landId)
            .apply()
    }

    override fun setTargetLanguageCode(languageCode: String) {
        preferences.edit()
            .putString(KEY_TARGET_LANGUAGE, languageCode)
            .apply()
    }


    override fun getThemeId(): Int{
        return preferences.getInt(KEY_THEME, AppSettings.DEFAULT_THEME_ID)
    }

    override fun getLandId(): Int{
        return preferences.getInt(KEY_LAND, AppSettings.NO_LAND_ID)
    }

    override fun getTargetLanguageCode(): String {
        var defaultLanguage = Locale.getDefault().language.uppercase()
        if (defaultLanguage == AppSettings.DEFAULT_TARGET_LANGUAGE_ID){
            defaultLanguage = DeepLLanguages.ENGLISH_AMERICAN.value
        }

        return preferences.getString(KEY_TARGET_LANGUAGE, defaultLanguage).toString()
    }
}