package com.example.running.other

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.running.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelTrackingDialog : DialogFragment() {

    private var yesListener: (() -> Unit)? = null

    fun setYesListener(listener: () -> Unit) {
        yesListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cancel the Run?")
            .setMessage("Are you sure you want to cancel the run and delete its data?")
            .setIcon(R.drawable.ic_round_close_24)
            .setPositiveButton("Yes") { _, _ ->
                yesListener?.let { yes->
                    yes()
                }
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
            .show()

    }
}