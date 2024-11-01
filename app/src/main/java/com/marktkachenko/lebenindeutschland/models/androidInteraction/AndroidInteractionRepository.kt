package com.marktkachenko.lebenindeutschland.models.androidInteraction

interface AndroidInteractionRepository {

    fun copyToClipboard(string: String)

    fun openUrl(url: String)
}