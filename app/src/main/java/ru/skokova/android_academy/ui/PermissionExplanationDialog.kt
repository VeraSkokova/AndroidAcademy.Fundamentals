package ru.skokova.android_academy.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ru.skokova.android_academy.R
import ru.skokova.android_academy.viewmodel.PermissionViewModel

class PermissionExplanationDialog : DialogFragment() {
    private val permissionViewModel: PermissionViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity()
        return AlertDialog.Builder(activity)
            .setMessage(R.string.calendar_permission_explanation)
            .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                permissionViewModel.onRationaleShown(true)
                permissionViewModel.onRequestPermission(true)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}