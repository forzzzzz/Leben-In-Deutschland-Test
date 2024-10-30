package com.marktkachenko.lebenindeutschland.settings

import android.content.Context
import android.content.SharedPreferences
import com.marktkachenko.lebenindeutschland.enums.DeepLLanguages
import java.util.Locale

class Preferences(context: Context) {

    companion object {
        private const val KEY_THEME = "marktkachenko.lebenindeutschland.THEME"
        private const val KEY_LAND = "marktkachenko.lebenindeutschland.LAND"
        private const val KEY_TARGET_LANGUAGE = "marktkachenko.lebenindeutschland.TARGET_LANGUAGE"
    }

    private val preferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun setSettings(){
        val editor = preferences.edit()
        editor.putInt(KEY_THEME, Config.theme)
        editor.putInt(KEY_LAND, Config.land)
        editor.putString(KEY_TARGET_LANGUAGE, Config.targetLanguage)
        editor.apply()
    }





    private fun getTheme(): Int{
        return preferences.getInt(KEY_THEME, -1)
    }

    private fun getLand(): Int{
        return preferences.getInt(KEY_LAND, 0)
    }

    private fun getTargetLanguage(): String {
        var defaultLanguage = Locale.getDefault().language.uppercase()
        if (defaultLanguage == "EN"){
            defaultLanguage = DeepLLanguages.ENGLISH_AMERICAN.value
        }

        return preferences.getString(KEY_TARGET_LANGUAGE, defaultLanguage).toString()
    }



    fun loadConfig() {
        Config.theme = getTheme()
        Config.land = getLand()
        Config.targetLanguage = getTargetLanguage()
    }
}