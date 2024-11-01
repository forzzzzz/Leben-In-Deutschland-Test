package com.marktkachenko.lebenindeutschland.screens.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.Repositories
import com.marktkachenko.lebenindeutschland.models.questions.Lands
import com.marktkachenko.lebenindeutschland.screens.main.MainActivityViewModel
import com.marktkachenko.lebenindeutschland.utils.viewModelCreator

class LandsDialogFragment : DialogFragment() {

    private val viewModel by viewModelCreator {MainActivityViewModel(Repositories.appSettings)}


    companion object {
        const val TAG_LANDS_DIALOG = "LANDS_DIALOG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)

            val landNames = Lands.entries.map { getString(it.nameId) }.toTypedArray()
            var selectedLandIndex = Lands.entries.toTypedArray().indexOfFirst { it.value == viewModel.landId.value }

            builder.setTitle(getString(R.string.land_title))
                .setSingleChoiceItems(landNames, selectedLandIndex) { _, which ->
                    selectedLandIndex = which
                }
                .setPositiveButton(getString(R.string.ok_dialog)) { _, _ ->
                    val selectedLandId = Lands.entries[selectedLandIndex].value
                    viewModel.setLand(selectedLandId)
                }
                .setNegativeButton(getString(R.string.cancel_dialog)) { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
