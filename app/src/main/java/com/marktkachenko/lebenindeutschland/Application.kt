package com.marktkachenko.lebenindeutschland

import android.app.Application
import android.os.Build
import com.google.android.material.color.DynamicColors

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                setTheme(R.style.Theme_DynamicColor_LebenInDeutschland)
                DynamicColors.applyToActivitiesIfAvailable(this)
            }
    }
}