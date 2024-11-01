package com.marktkachenko.lebenindeutschland.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.databinding.ActivityMainBinding
import com.marktkachenko.lebenindeutschland.screens.dialogs.LandsDialogFragment
import com.marktkachenko.lebenindeutschland.models.Fragments
import com.marktkachenko.lebenindeutschland.models.MainFragments
import com.marktkachenko.lebenindeutschland.screens.settings.SettingsActivity
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModelCreator { MainActivityViewModel(Repositories.appSettings) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.showLandsDialogEvent.observe(this) {
            if (it) {
                LandsDialogFragment().show(supportFragmentManager, LandsDialogFragment.TAG_LANDS_DIALOG)
                viewModel.onLandsDialogShown()
            }
        }

        binding.bottomNavigation.selectedItemId = Fragments.correctMainFragment.tabId
        showFragment(Fragments.correctMainFragment.fragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                MainFragments.TEST_FRAGMENT.tabId -> {
                    Fragments.correctMainFragment = MainFragments.TEST_FRAGMENT
                    showFragment(Fragments.correctMainFragment.fragment)
                }
                MainFragments.STATISTIC_FRAGMENT.tabId -> {
                    Fragments.correctMainFragment = MainFragments.STATISTIC_FRAGMENT
                    showFragment(Fragments.correctMainFragment.fragment)
                }
            }
            true
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit()
    }
}