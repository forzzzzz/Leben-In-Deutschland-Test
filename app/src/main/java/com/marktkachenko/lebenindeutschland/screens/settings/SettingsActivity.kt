package com.marktkachenko.lebenindeutschland.screens.settings

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.button.MaterialButtonToggleGroup
import com.marktkachenko.lebenindeutschland.BaseActivity
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.databinding.SettingsActivityBinding
import com.marktkachenko.lebenindeutschland.screens.dialogs.DeepLLanguagesDialogFragment
import com.marktkachenko.lebenindeutschland.screens.dialogs.LandsDialogFragment
import com.marktkachenko.lebenindeutschland.models.settings.AppThemes
import com.marktkachenko.lebenindeutschland.screens.main.tabs.TestFragmentViewModel
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class SettingsActivity : BaseActivity() {

    private lateinit var binding: SettingsActivityBinding

    private val viewModel by viewModelCreator { SettingsActivityViewModel(Repositories.appSettings, Repositories.androidInteractionRepository) }
    private val testViewModel by viewModelCreator { TestFragmentViewModel(Repositories.testsRepository, Repositories.appSettings) }

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

        viewModel.showLandsDialogEvent.observe(this) { showDialog ->
            if (showDialog) {
                LandsDialogFragment(testViewModel, Repositories.appSettings).show(supportFragmentManager, LandsDialogFragment.TAG_LANDS_DIALOG)
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
                    R.id.themeAutoButton -> AppThemes.DEFAULT.value
                    R.id.themeLightButton -> AppThemes.LIGHT.value
                    R.id.themeDarkButton -> AppThemes.DARK.value
                    R.id.themeAMOLEDButton -> AppThemes.AMOLED.value
                    else -> return@addOnButtonCheckedListener
                }

                val currentNightMode = viewModel.themeId.value
                if (currentNightMode != newNightMode) {
                    viewModel.setThemeId(newNightMode)

                    if (newNightMode == AppThemes.AMOLED.value) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                            setTheme(R.style.Theme_DynamicColor_AMOLED_LebenInDeutschland)
                        } else{
                            setTheme(R.style.Theme_AMOLED_LebenInDeutschland)
                        }
                    }else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                            setTheme(R.style.Theme_DynamicColor_LebenInDeutschland)
                        } else{
                            setTheme(R.style.Theme_LebenInDeutschland)
                        }
                    }

                    AppCompatDelegate.setDefaultNightMode(newNightMode)

                    sendThemeChangedBroadcast()
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
        AppThemes.entries.find { it.value == value }?.let { themeEntry ->
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

    private fun sendThemeChangedBroadcast() {
        val intent = Intent(ACTION_THEME_CHANGED)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}