package com.marktkachenko.lebenindeutschland

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.marktkachenko.lebenindeutschland.databinding.ActivityMainBinding
import com.marktkachenko.lebenindeutschland.dialogFragments.LandsDialogFragment
import com.marktkachenko.lebenindeutschland.settings.Config
import com.marktkachenko.lebenindeutschland.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (Config.land == 0){
            LandsDialogFragment().show(supportFragmentManager,  LandsDialogFragment.TAG_LANDS_DIALOG)
        }

        binding.bottomNavigation.selectedItemId = R.id.test
        showFragment(TestFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.test -> {
                    showFragment(TestFragment())
                }
                R.id.statistic -> {
                    showFragment(StatisticFragment())
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