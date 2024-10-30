package com.marktkachenko.lebenindeutschland.settings

import kotlin.properties.Delegates.notNull

object Config {
    var theme: Int by notNull()
    var land: Int by notNull()
    var targetLanguage: Int by notNull()
}