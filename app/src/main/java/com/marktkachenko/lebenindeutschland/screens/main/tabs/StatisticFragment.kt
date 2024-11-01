package com.marktkachenko.lebenindeutschland.screens.main.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.databinding.FragmentStatisticBinding
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class StatisticFragment : Fragment() {

    private lateinit var binding: FragmentStatisticBinding

    private val viewModel by viewModelCreator { StatisticFragmentViewModel(Repositories.questionsRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)


        return binding.root
    }
}