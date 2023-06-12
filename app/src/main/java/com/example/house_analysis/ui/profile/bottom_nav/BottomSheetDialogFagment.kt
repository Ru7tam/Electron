package com.example.house_analysis.ui.profile.bottom_nav

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.house_analysis.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BottomSheetDialogFagment: BottomSheetDialogFragment() {

    private lateinit var chipList : ChipGroup
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet, null)


        // Customize the views in the bottom sheet dialog
        dialog.setContentView(view)

        dialog.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT // Set width to match parent
        dialog.window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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

        val view = LayoutInflater.from(context).inflate(R.layout.add_label_dialog, null)


        val dialog = Dialog(requireContext())
        dialog.setContentView(view)

        chipList = view.findViewById(R.id.chipGroup)
        val addLabelBtn = view.findViewById<Button>(R.id.addLabelBtn)
        val addLabelEt = view.findViewById<TextInputEditText>(R.id.addLabelEt)

        addLabelBtn.setOnClickListener {
            addChip(addLabelEt.text.toString())
            addLabelEt.text = null
        }

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

    private fun addChip(text2: String) {
        d("mytag", "$text2")
        val chip1 = Chip(requireContext())
        chip1.setChipBackgroundColorResource(R.color.labelColor)
        chip1.setTextColor(resources.getColor(R.color.white))
        chip1.text = "@" + text2
        chipList.addView(chip1)

    }
}
