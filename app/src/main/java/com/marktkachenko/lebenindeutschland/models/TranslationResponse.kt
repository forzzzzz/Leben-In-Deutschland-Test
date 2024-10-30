package com.marktkachenko.lebenindeutschland.models

data class TranslationResponse(
    val translations: List<Translation>
)

data class Translation(
    val text: String
)
