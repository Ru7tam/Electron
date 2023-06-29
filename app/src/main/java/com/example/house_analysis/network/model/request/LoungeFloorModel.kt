package com.example.house_analysis.network.model.request

data class LoungeFloorModel(
    val floorNumber: Int,
    val loungeNumber: Int,
    val subtaskIds: List<Int>
)
