package com.example.house_analysis.ui.profile.bottom_nav

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.house_analysis.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogFagment: BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet, null)

        // Customize the views in the bottom sheet dialog

        dialog.setContentView(view)
        val closeBtn = dialog.findViewById<Button>(R.id.cancel)
        closeBtn?.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }

}