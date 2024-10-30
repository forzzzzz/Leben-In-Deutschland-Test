package com.marktkachenko.lebenindeutschland

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import com.marktkachenko.lebenindeutschland.settings.Config
import com.marktkachenko.lebenindeutschland.settings.Preferences

class Application: Application() {
    val preferences: Preferences by lazy {
        Preferences(this)
    }

    override fun onCreate() {
        super.onCreate()
        preferences.loadConfig()

        when (Config.theme) {
            -1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            setTheme(R.style.Theme_DynamicColor_LebenInDeutschland)
            DynamicColors.applyToActivitiesIfAvailable(this)
        }
    }
}