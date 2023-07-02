package com.example.house_analysis.ui.profile.bottom_nav.tasks.recycler

data class Tasks(
    val street: String,
    val apartment: List<Int>,
    val floors: List<Int>,
    val isChecked: Boolean
)
