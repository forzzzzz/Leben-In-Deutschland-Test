package com.marktkachenko.lebenindeutschland.enums

import com.marktkachenko.lebenindeutschland.R

enum class Themes (val buttonId: Int, val value: Int) {
    DEFAULT(R.id.themeAutoButton, -1),
    LIGHT(R.id.themeLightButton, 1),
    DARK(R.id.themeDarkButton, 2)
}