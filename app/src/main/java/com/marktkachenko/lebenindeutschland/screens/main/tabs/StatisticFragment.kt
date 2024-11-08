package com.marktkachenko.lebenindeutschland.screens.main.tabs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.databinding.FragmentStatisticBinding
import com.marktkachenko.lebenindeutschland.models.statistics.Statistic
import com.marktkachenko.lebenindeutschland.models.statistics.StatisticActionListener
import com.marktkachenko.lebenindeutschland.models.statistics.StatisticAdapter
import com.marktkachenko.lebenindeutschland.screens.main.MainActivity
import com.marktkachenko.lebenindeutschland.screens.settings.SettingsActivity
import com.marktkachenko.lebenindeutschland.screens.test.TestActivity
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

@Suppress("DEPRECATION")
class StatisticFragment : Fragment() {

    private lateinit var binding: FragmentStatisticBinding
    private lateinit var adapter: StatisticAdapter

    private val viewModel by viewModelCreator { StatisticFragmentViewModel(Repositories.statisticsRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)

        adapter = StatisticAdapter(object : StatisticActionListener {
            override fun onStartTest(statistic: Statistic) {
                TestActivity.testFilter = statistic.testFilter
                TestActivity.testFilterNumber = statistic.testFilterNumber

                val intent = Intent(requireActivity(), TestActivity::class.java)
                startActivityForResult(intent, MainActivity.REQUEST_CODE_CHILD)
            }
        })

        viewModel.statistics.observe(viewLifecycleOwner) {
            adapter.statistics = it
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.statisticsRecyclerView.layoutManager = layoutManager
        binding.statisticsRecyclerView.adapter = adapter

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    val intent = Intent(requireActivity(), SettingsActivity::class.java)
                    startActivityForResult(intent, MainActivity.REQUEST_CODE_CHILD)
                    true
                }
                else -> false
            }
        }

        return binding.root
    }
}