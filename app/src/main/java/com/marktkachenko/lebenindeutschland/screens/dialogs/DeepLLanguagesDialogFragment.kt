package com.marktkachenko.lebenindeutschland.screens.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.models.deepLTranslate.DeepLLanguages
import com.marktkachenko.lebenindeutschland.screens.settings.SettingsActivityViewModel
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class DeepLLanguagesDialogFragment : DialogFragment() {

    private val viewModel by viewModelCreator { SettingsActivityViewModel(Repositories.appSettings, Repositories.androidInteractionRepository) }

    companion object {
        const val TAG_DEEPL_LANGUAGES_DIALOG = "DEEPL_LANGUAGES_DIALOG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)

            val languagesNames = DeepLLanguages.entries.map { getString(it.nameId) }.toTypedArray()
            var selectedLandIndex = DeepLLanguages.entries.toTypedArray().indexOfFirst { it.value == viewModel.targetLanguageCode.value }

            builder.setTitle(getString(R.string.target_language_title))
                .setSingleChoiceItems(languagesNames, selectedLandIndex) { _, which ->
                    selectedLandIndex = which
                }
                .setPositiveButton(getString(R.string.ok_dialog)) { _, _ ->
                    val selectedLandId = DeepLLanguages.entries[selectedLandIndex].value
                    viewModel.setTargetLanguageCode(selectedLandId)
                }
                .setNegativeButton(getString(R.string.cancel_dialog)) { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
