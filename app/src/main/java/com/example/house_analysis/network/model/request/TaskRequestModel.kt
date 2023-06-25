package com.example.house_analysis.network.model.request

data class TaskRequestModel(
    val title: String,
    val from: Int,
    val to: Int,
)