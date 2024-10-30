package com.marktkachenko.lebenindeutschland.enums

import androidx.fragment.app.Fragment
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.StatisticFragment
import com.marktkachenko.lebenindeutschland.TestFragment

enum class MainFragments(val fragment: Fragment, val tabId: Int) {
    TEST_FRAGMENT(TestFragment(), R.id.test),
    STATISTIC_FRAGMENT(StatisticFragment(), R.id.statistic)
}

object Fragments {
    var correctMainFragment = MainFragments.TEST_FRAGMENT
}