package com.marktkachenko.lebenindeutschland.models.settings

import com.marktkachenko.lebenindeutschland.R

enum class AppThemes (val buttonId: Int, val value: Int) {
    DEFAULT(R.id.themeAutoButton, -1),
    LIGHT(R.id.themeLightButton, 1),
    DARK(R.id.themeDarkButton, 2),
    AMOLED(R.id.themeAMOLEDButton, 3)
}