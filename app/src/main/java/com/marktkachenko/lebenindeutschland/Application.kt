package com.marktkachenko.lebenindeutschland

import android.app.Application

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Repositories.init(this)
    }
}