package com.marktkachenko.lebenindeutschland

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Repositories.init(this)
        val appSettings = Repositories.appSettings

        when (appSettings.getThemeId()) {
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