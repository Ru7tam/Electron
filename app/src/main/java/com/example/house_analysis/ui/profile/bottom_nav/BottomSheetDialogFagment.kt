package com.example.house_analysis.ui.profile.bottom_nav

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.allyants.chipview.ChipView
import com.example.house_analysis.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class BottomSheetDialogFagment: BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet, null)

        // Customize the views in the bottom sheet dialog

        dialog.setContentView(view)

        val unite = view.findViewById<TextView>(R.id.unit)
        unite.setOnClickListener {
            unitDialog()
        }

        val addLabel = view.findViewById<TextView>(R.id.add_label)
        addLabel.setOnClickListener {
            add_label()
        }

        val closeBtn = dialog.findViewById<Button>(R.id.cancel)
        closeBtn?.setOnClickListener {
            dialog.dismiss()
        }


        return dialog
    }

    private fun unitDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.unit_dialog)

        // Set dialog width and height
        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT // Set width to match parent
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT // Set height to wrap content
        dialog.findViewById<ImageView>(R.id.closeDialog).setOnClickListener {
            dialog.dismiss()
        }
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun add_label(){


        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.add_label_dialog)

        // Set dialog width and height
        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT // Set width to match parent
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT // Set height to wrap content
        dialog.findViewById<ImageView>(R.id.closeDialog).setOnClickListener {
            dialog.dismiss()
        }
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }

}