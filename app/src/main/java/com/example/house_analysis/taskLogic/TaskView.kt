package com.example.house_analysis.taskLogic

import android.content.Context
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.example.house_analysis.R

class TaskView(context: Context): LinearLayout(context) {
    var checkBox: CheckBox? = null
    var checkBox1: CheckBox? = null
    var address: TextView? = null
    var apartmentLis: TextView? = null
    var floorList: TextView? = null

    init {
        inflate(context, R.layout.task_item, this)

        checkBox = findViewById(R.id.isChecked)
        checkBox1 = findViewById(R.id.isChecked1)
        address = findViewById(R.id.taskAddress)
        apartmentLis = findViewById(R.id.apartmentList)
        floorList = findViewById(R.id.floorList)
    }
}