package com.marktkachenko.lebenindeutschland.screens.settings

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButtonToggleGroup
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.databinding.SettingsActivityBinding
import com.marktkachenko.lebenindeutschland.screens.dialogs.DeepLLanguagesDialogFragment
import com.marktkachenko.lebenindeutschland.screens.dialogs.LandsDialogFragment
import com.marktkachenko.lebenindeutschland.models.settings.Themes
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding

    private val viewModel by viewModelCreator { SettingsActivityViewModel(Repositories.appSettings, Repositories.androidInteractionRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = SettingsActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        viewModel.themeId.observe(this) {
            loadThemeImage(it)
            loadThemeButtonToggleGroup(binding.themeButtonToggleGroup, it)
        }

        viewModel.showLandsDialogEvent.observe(this) {
            if (it) {
                LandsDialogFragment().show(supportFragmentManager, LandsDialogFragment.TAG_LANDS_DIALOG)
                viewModel.onLandsDialogShown()
            }
        }

        viewModel.showDeepLLanguageDialogEvent.observe(this) {
            if (it) {
                DeepLLanguagesDialogFragment().show(supportFragmentManager, DeepLLanguagesDialogFragment.TAG_DEEPL_LANGUAGES_DIALOG)
                viewModel.onDeepLLanguageDialogShown()
            }
        }

        viewModel.setVersion(getAppVersionName())

        viewModel.version.observe(this) {
            binding.versionSubtitleText.text = it
        }

        binding.themeButtonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val newNightMode = when (checkedId) {
                    R.id.themeAutoButton -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    R.id.themeLightButton -> AppCompatDelegate.MODE_NIGHT_NO
                    R.id.themeDarkButton -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> return@addOnButtonCheckedListener
                }

                val currentNightMode = viewModel.themeId.value
                if (currentNightMode != newNightMode) {
                    viewModel.setThemeId(newNightMode)
                    AppCompatDelegate.setDefaultNightMode(newNightMode)
                }
            }
        }

        binding.landConstraintLayout.setOnClickListener {
            viewModel.showLandsDialog()
        }

        binding.targetLanguageConstraintLayout.setOnClickListener {
            viewModel.showDeepLLanguageDialog()
        }

        binding.versionConstraintLayout.setOnLongClickListener {
            viewModel.copyVersionToClipboard()
            true
        }

        binding.openSourceCodeConstraintLayout.setOnClickListener {
            viewModel.openSourceCodeUrl(getString(R.string.git_hub_url))
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadThemeImage(themeId: Int) {
        when (themeId) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> binding.themeImageView.setImageResource(R.drawable.baseline_dark_mode_24)
                    Configuration.UI_MODE_NIGHT_NO -> binding.themeImageView.setImageResource(R.drawable.baseline_light_mode_24)
                }
            }
            AppCompatDelegate.MODE_NIGHT_NO -> binding.themeImageView.setImageResource(R.drawable.baseline_light_mode_24)
            else -> binding.themeImageView.setImageResource(R.drawable.baseline_dark_mode_24)
        }
    }

    private fun loadThemeButtonToggleGroup(buttonToggleGroup: MaterialButtonToggleGroup, value: Int) {
        Themes.entries.find { it.value == value }?.let { themeEntry ->
            buttonToggleGroup.check(themeEntry.buttonId)
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
}