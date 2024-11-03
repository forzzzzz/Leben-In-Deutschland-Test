package com.marktkachenko.lebenindeutschland

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.color.DynamicColors
import com.marktkachenko.lebenindeutschland.models.settings.AppThemes

open class BaseActivity : AppCompatActivity() {

    private val themeChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val appSettings = Repositories.appSettings

        when (appSettings.getThemeId()) {
            AppThemes.DEFAULT.value, AppThemes.LIGHT.value, AppThemes.DARK.value -> applyTheme()
            AppThemes.AMOLED.value -> applyAMOLEDTheme()
        }

        applyThemeSettings(appSettings.getThemeId())

        super.onCreate(savedInstanceState)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(themeChangeReceiver, IntentFilter(ACTION_THEME_CHANGED))
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(themeChangeReceiver)
    }


    private fun applyAMOLEDTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setTheme(R.style.Theme_DynamicColor_AMOLED_LebenInDeutschland)
            DynamicColors.applyIfAvailable(this, R.style.Theme_DynamicColor_AMOLED_LebenInDeutschland)
        } else {
            setTheme(R.style.Theme_AMOLED_LebenInDeutschland)
        }
    }

    private fun applyTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setTheme(R.style.Theme_DynamicColor_LebenInDeutschland)
            DynamicColors.applyIfAvailable(this, R.style.Theme_DynamicColor_LebenInDeutschland)
        } else {
            setTheme(R.style.Theme_LebenInDeutschland)
        }
    }

    private fun applyThemeSettings(themeId: Int) {
        when (themeId) {
            -1 -> AppCompatDelegate.setDefaultNightMode(AppThemes.DEFAULT.value)
            1 -> AppCompatDelegate.setDefaultNightMode(AppThemes.LIGHT.value)
            2 -> AppCompatDelegate.setDefaultNightMode(AppThemes.DARK.value)
            3 -> AppCompatDelegate.setDefaultNightMode(AppThemes.DARK.value)
        }
    }

    companion object{
        const val ACTION_THEME_CHANGED = "ACTION_THEME_CHANGED"
    }
}