package com.marktkachenko.lebenindeutschland.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import com.marktkachenko.lebenindeutschland.BaseActivity
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.databinding.ActivityMainBinding
import com.marktkachenko.lebenindeutschland.models.Fragments
import com.marktkachenko.lebenindeutschland.models.MainFragments
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModelCreator { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == REQUEST_CODE_CHILD) {
            recreate()
        }
    }

    companion object {
        const val REQUEST_CODE_CHILD = 0
    }
}