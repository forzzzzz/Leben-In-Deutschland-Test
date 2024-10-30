package com.marktkachenko.lebenindeutschland

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.marktkachenko.lebenindeutschland.databinding.ActivityMainBinding
import com.marktkachenko.lebenindeutschland.dialogFragments.LandsDialogFragment
import com.marktkachenko.lebenindeutschland.enums.Fragments
import com.marktkachenko.lebenindeutschland.enums.MainFragments
import com.marktkachenko.lebenindeutschland.settings.Config
import com.marktkachenko.lebenindeutschland.settings.Preferences
import com.marktkachenko.lebenindeutschland.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val preferences: Preferences
        get() = (this.applicationContext as Application).preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (Config.land == 0){
            LandsDialogFragment().show(supportFragmentManager,  LandsDialogFragment.TAG_LANDS_DIALOG)
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

    override fun onStop() {
        super.onStop()

        preferences.setSettings()
    }
}