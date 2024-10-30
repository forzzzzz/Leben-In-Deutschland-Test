package com.marktkachenko.lebenindeutschland

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marktkachenko.lebenindeutschland.databinding.FragmentStatisticBinding

class StatisticFragment : Fragment() {

    private lateinit var binding: FragmentStatisticBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)


        return binding.root
    }
}