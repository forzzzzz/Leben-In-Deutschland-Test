package com.marktkachenko.lebenindeutschland.screens.main.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.databinding.FragmentTestBinding
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding

    private val viewModel by viewModelCreator { TestFragmentViewModel(Repositories.questionsRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)


        return binding.root
    }
}