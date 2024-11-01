package com.marktkachenko.lebenindeutschland.models.questions

import com.marktkachenko.lebenindeutschland.R

enum class Themes(val nameId: Int, val value: Int) {
    POLITICS_IN_DEMOCRACY(R.string.politics_in_democracy, 1),
    HISTORY_AND_RESPONSIBILITY(R.string.history_and_responsibility, 2),
    PEOPLE_AND_SOCIETY(R.string.people_and_society, 3)
}