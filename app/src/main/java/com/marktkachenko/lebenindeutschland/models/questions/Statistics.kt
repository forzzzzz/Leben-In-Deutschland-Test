package com.marktkachenko.lebenindeutschland.models.questions

import com.marktkachenko.lebenindeutschland.R

enum class Statistics(val nameId: Int, val value: Int) {
    AT_LEAST_ONCE_WRONG(R.string.at_least_once_wrong, 1),
    MOSTLY_WRONG(R.string.mostly_wrong, 2),
    LAST_ANSWER_WRONG(R.string.last_answer_wrong, 3),
    NOT_ANSWERED_YET(R.string.not_answered_yet, 4),
    LAST_ANSWER_RIGHT(R.string.last_answer_right, 5),
    MOSTLY_RIGHT(R.string.mostly_right, 6)
}