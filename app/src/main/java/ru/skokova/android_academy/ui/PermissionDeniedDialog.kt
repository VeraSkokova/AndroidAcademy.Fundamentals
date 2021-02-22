package ru.skokova.android_academy.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.skokova.android_academy.R

class PermissionDeniedDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity()
        return AlertDialog.Builder(activity)
            .setMessage(R.string.calendar_permission_denied)
            .setPositiveButton(R.string.dialog_positive_button) { dialog, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + requireContext().packageName)
                    )
                )
                dialog.dismiss()
            }
            .setNegativeButton(R.string.dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}