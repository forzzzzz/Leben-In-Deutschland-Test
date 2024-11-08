package com.marktkachenko.lebenindeutschland.screens.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.models.questions.Lands
import com.marktkachenko.lebenindeutschland.models.settings.AppSettings
import com.marktkachenko.lebenindeutschland.screens.main.tabs.TestFragmentViewModel

class LandsDialogFragment(
    private val viewModel: TestFragmentViewModel,
    private val appSettings: AppSettings
) : DialogFragment() {

    companion object {
        const val TAG_LANDS_DIALOG = "LANDS_DIALOG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)

            val landNames = Lands.entries.map { getString(it.nameId) }.toTypedArray()
            var selectedLandIndex = Lands.entries.toTypedArray().indexOfFirst { it.value == appSettings.getLandId() }

            builder.setTitle(getString(R.string.land_title))
                .setSingleChoiceItems(landNames, selectedLandIndex) { _, which ->
                    selectedLandIndex = which
                }
                .setPositiveButton(getString(R.string.ok_dialog)) { _, _ ->
                    val selectedLandId = Lands.entries[selectedLandIndex].value
                    appSettings.setLandId(selectedLandId)
                    viewModel.setLandId(selectedLandId)
                }
                .setNegativeButton(getString(R.string.cancel_dialog)) { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
