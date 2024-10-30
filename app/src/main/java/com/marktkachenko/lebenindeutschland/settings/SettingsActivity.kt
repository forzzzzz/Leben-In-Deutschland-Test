package com.marktkachenko.lebenindeutschland.settings

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButtonToggleGroup
import com.marktkachenko.lebenindeutschland.Application
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.databinding.SettingsActivityBinding
import com.marktkachenko.lebenindeutschland.dialogFragments.DeepLLanguagesDialogFragment
import com.marktkachenko.lebenindeutschland.dialogFragments.LandsDialogFragment
import com.marktkachenko.lebenindeutschland.enums.Themes
import com.marktkachenko.lebenindeutschland.utils.InteractionAndroid

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding
    private val preferences: Preferences
        get() = (this.applicationContext as Application).preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = SettingsActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadSettings()

        binding.themeButtonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val newNightMode = when (checkedId) {
                    R.id.themeAutoButton -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    R.id.themeLightButton -> AppCompatDelegate.MODE_NIGHT_NO
                    R.id.themeDarkButton -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> return@addOnButtonCheckedListener
                }

                val currentNightMode = Config.theme
                Config.theme = currentNightMode
                if (currentNightMode != newNightMode) {
                    Config.theme = newNightMode
                    AppCompatDelegate.setDefaultNightMode(Config.theme)
                }
            }
        }

        binding.landConstraintLayout.setOnClickListener {
            LandsDialogFragment().show(supportFragmentManager,  LandsDialogFragment.TAG_LANDS_DIALOG)
        }

        binding.targetLanguageConstraintLayout.setOnClickListener {
            DeepLLanguagesDialogFragment().show(supportFragmentManager,   DeepLLanguagesDialogFragment.TAG_DEEPL_LANGUAGES_DIALOG)
        }

        binding.versionConstraintLayout.setOnLongClickListener {
            InteractionAndroid.copyToClipboard(getAppVersionName(), this)
            true
        }

        binding.openSourceCodeConstraintLayout.setOnClickListener {
            InteractionAndroid.openUrl(getString(R.string.git_hub_url), this)
        }


        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }



    private fun loadSettings(){
        binding.versionSubtitleText.text = getAppVersionName()
        loadThemeImage()
        loadThemeButtonToggleGroup(
            binding.themeButtonToggleGroup,
            Config.theme
        )
    }

    private fun loadThemeImage(){
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                when (this@SettingsActivity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        binding.themeImageView.setImageResource(R.drawable.baseline_dark_mode_24)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        binding.themeImageView.setImageResource(R.drawable.baseline_light_mode_24)
                    }
                }
            }
            AppCompatDelegate.MODE_NIGHT_NO -> {
                binding.themeImageView.setImageResource(R.drawable.baseline_light_mode_24)
            }
            else -> {
                binding.themeImageView.setImageResource(R.drawable.baseline_dark_mode_24)
            }
        }
    }

    private fun loadThemeButtonToggleGroup(buttonToggleGroup: MaterialButtonToggleGroup, value: Int) {
        Themes.entries.find { it.value == value }?.let {
            buttonToggleGroup.check(it.buttonId)
        }
    }

    private fun getAppVersionName(): String {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            getString(R.string.version_error)
        }
    }

    override fun onStop() {
        super.onStop()

        preferences.setSettings()
    }
}