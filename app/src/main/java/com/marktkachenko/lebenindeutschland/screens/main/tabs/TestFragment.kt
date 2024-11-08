package com.marktkachenko.lebenindeutschland.screens.main.tabs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.databinding.FragmentTestBinding
import com.marktkachenko.lebenindeutschland.models.tests.Test
import com.marktkachenko.lebenindeutschland.models.tests.TestActionListener
import com.marktkachenko.lebenindeutschland.models.tests.TestsAdapter
import com.marktkachenko.lebenindeutschland.screens.dialogs.LandsDialogFragment
import com.marktkachenko.lebenindeutschland.screens.settings.SettingsActivity
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.models.tests.TestFilter
import com.marktkachenko.lebenindeutschland.screens.main.MainActivity
import com.marktkachenko.lebenindeutschland.screens.test.TestActivity
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

@Suppress("DEPRECATION")
class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private lateinit var adapter: TestsAdapter

    private lateinit var startActivityLauncher: ActivityResultLauncher<Intent>

    private val viewModel by viewModelCreator { TestFragmentViewModel(Repositories.testsRepository, Repositories.appSettings) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)

        startActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.updateLandInTest()
        }

        adapter = TestsAdapter(object : TestActionListener{
            override fun onStartTest(test: Test) {
                if (test.testFilter == TestFilter.LAND.id && viewModel.landId.value == AppSettings.NO_LAND_ID){
                    viewModel.showLandsDialog()
                }

                TestActivity.testFilter = test.testFilter
                TestActivity.testFilterNumber = test.testFilterNumber

                val intent = Intent(requireActivity(), TestActivity::class.java)
                startActivityForResult(intent, MainActivity.REQUEST_CODE_CHILD)
            }
        })

        viewModel.tests.observe(viewLifecycleOwner) {
            adapter.tests = it
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.testsRecyclerView.layoutManager = layoutManager
        binding.testsRecyclerView.adapter = adapter

        viewModel.showLandsDialogEvent.observe(viewLifecycleOwner) { showDialog ->
            if (showDialog) {
                LandsDialogFragment(viewModel, Repositories.appSettings).show(parentFragmentManager, LandsDialogFragment.TAG_LANDS_DIALOG)
                viewModel.onLandsDialogShown()
            }
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    val intent = Intent(requireActivity(), SettingsActivity::class.java)
                    startActivityLauncher.launch(intent)
                    true
                }
                else -> false
            }
        }

        viewModel.landId.observe(viewLifecycleOwner) {
            viewModel.updateLandInTest()
        }

        binding.mainActionButton.setOnClickListener {
            TestActivity.testFilter = 0
            TestActivity.testFilterNumber = 0

            val intent = Intent(requireActivity(), TestActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}