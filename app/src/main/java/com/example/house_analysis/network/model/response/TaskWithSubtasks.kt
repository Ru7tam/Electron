package com.example.house_analysis.network.model.response

data class TaskWithSubtasks(
    val taskId: Int,
    val title: String,
    val totalSubtasks: Int,
    val subtasks: List<Subtask>
)

data class Subtask(
    val number: Int,
    val subtaskId: Int,
    val priority: String?,
    val status: String,
    val floor: Int,
    val lounge: Int,
    val isDescribed: Boolean,
    val isMarked: Boolean
)