package com.example.house_analysis.network.model.response

data class TasksResponse(
    val taskId: Int,
    val title: String,
    val floorsFrom: Int,
    val floorsTo: Int,
    val loungesFrom: Int,
    val loungesTo: Int,
    val subtasksFrom: Int,
    val subtasksTo: Int
)
