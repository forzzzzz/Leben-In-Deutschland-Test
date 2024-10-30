package com.marktkachenko.lebenindeutschland.dialogFragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.marktkachenko.lebenindeutschland.R
import com.marktkachenko.lebenindeutschland.enums.Lands
import com.marktkachenko.lebenindeutschland.settings.Config

class LandsDialogFragment : DialogFragment() {
    companion object {
        const val TAG_LANDS_DIALOG = "LANDS_DIALOG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)

            val landNames = Lands.entries.map { getString(it.text) }.toTypedArray()
            var selectedLandIndex = Lands.entries.toTypedArray().indexOfFirst { it.value == Config.land }

            builder.setTitle(getString(R.string.land_title))
                .setSingleChoiceItems(landNames, selectedLandIndex) { _, which ->
                    selectedLandIndex = which
                }
                .setPositiveButton(getString(R.string.ok_dialog)) { _, _ ->
                    Config.land = Lands.entries[selectedLandIndex].value
                }
                .setNegativeButton(getString(R.string.cancel_dialog)) { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
