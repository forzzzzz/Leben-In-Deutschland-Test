package com.marktkachenko.lebenindeutschland.models.deepLTranslate

data class TranslationResponse(
    val translations: List<Translation>
)

data class Translation(
    val text: String
)
