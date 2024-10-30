package com.marktkachenko.lebenindeutschland.dialogFragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.enums.DeepLLanguages
import com.marktkachenko.lebenindeutschland.settings.Config

class DeepLLanguagesDialogFragment : DialogFragment() {
    companion object {
        const val TAG_DEEPL_LANGUAGES_DIALOG = "DEEPL_LANGUAGES_DIALOG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)

            val languagesNames = DeepLLanguages.entries.map { getString(it.nameId) }.toTypedArray()
            var selectedLandIndex = DeepLLanguages.entries.toTypedArray().indexOfFirst { it.value == Config.targetLanguage }

            builder.setTitle(getString(R.string.target_language_title))
                .setSingleChoiceItems(languagesNames, selectedLandIndex) { _, which ->
                    selectedLandIndex = which
                }
                .setPositiveButton(getString(R.string.ok_dialog)) { _, _ ->
                    Config.targetLanguage = DeepLLanguages.entries[selectedLandIndex].value
                }
                .setNegativeButton(getString(R.string.cancel_dialog)) { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
