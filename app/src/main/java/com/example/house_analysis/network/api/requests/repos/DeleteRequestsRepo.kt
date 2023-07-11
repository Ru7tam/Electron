package com.example.house_analysis.network.api.requests.repos

import com.example.house_analysis.network.api.requests.RequestProvider

class DeleteRequestsRepo(private val networkRepository: RequestProvider) {
    private val logTag = "Network"

    fun deleteTask(taskId: Int) = networkRepository.deleteTask(taskId)
}