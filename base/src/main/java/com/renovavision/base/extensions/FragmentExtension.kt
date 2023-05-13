package com.renovavision.base.extensions

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.renovavision.base.R

fun Fragment.navByUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (intent.resolveActivity(requireContext().packageManager) != null) {
        startActivity(intent)
    } else {
        Toast.makeText(
            requireContext(),
            "Application not found",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Fragment.getProgressDialog() =
    MaterialAlertDialogBuilder(requireContext())
        .setView(R.layout.dialog_fragment_progress)
        .setCancelable(false)
        .create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }