package com.marktkachenko.lebenindeutschland.models.questions

import com.marktkachenko.lebenindeutschland.R

enum class Statistics(val titleId: Int, val subTitleId: Int, val decorLine: Int, val value: Int) {
    MOSTLY_WRONG(R.string.mostly_wrong_title, R.string.mostly_wrong_subtitle, 0xFFC55C5C.toInt(), 2),
    LAST_ANSWER_WRONG(R.string.last_answer_wrong_title, R.string.last_answer_wrong_subtitle, 0xFFD18585.toInt(), 3),
    NOT_ANSWERED_YET(R.string.not_answered_yet_title, R.string.not_answered_yet_subtitle, 0xFF808080.toInt(), 4),
    LAST_ANSWER_RIGHT(R.string.last_answer_right_title, R.string.last_answer_right_subtitle, 0xFF99B88E.toInt(), 5),
    MOSTLY_RIGHT(R.string.mostly_right_title, R.string.mostly_right_subtitle, 0xFF76A664.toInt(), 6)
}