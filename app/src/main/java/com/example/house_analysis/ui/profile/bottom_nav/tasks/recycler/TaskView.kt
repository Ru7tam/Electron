package com.example.house_analysis.ui.profile.bottom_nav.tasks.recycler

import android.content.Context
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.example.house_analysis.R

class TaskView(context: Context): LinearLayout(context) {
    var checkBox: CheckBox? = null
    var address: TextView? = null
    var apartmentLis: TextView? = null
    var floorList: TextView? = null

    init {
        inflate(context, R.layout.task_item, this)

        checkBox = findViewById(R.id.isChecked)
        address = findViewById(R.id.taskAddress)
        apartmentLis = findViewById(R.id.apartmentList)
        floorList = findViewById(R.id.floorList)
    }
}