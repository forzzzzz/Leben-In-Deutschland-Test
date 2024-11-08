package com.marktkachenko.lebenindeutschland.models.questions

import com.marktkachenko.lebenindeutschland.R

enum class Themes(val nameId: Int, val numberOfQuestions: Int, val value: Int, val icon: Int) {
    POLITICS_IN_DEMOCRACY(R.string.politics_in_democracy, 192, 1, R.drawable.baseline_account_balance_24),
    HISTORY_AND_RESPONSIBILITY(R.string.history_and_responsibility, 89, 2, R.drawable.baseline_public_24),
    PEOPLE_AND_SOCIETY(R.string.people_and_society, 19, 3, R.drawable.baseline_people_24)
}