package com.example.house_analysis.network.api

import com.example.house_analysis.network.model.request.TaskRequestModel
import io.reactivex.Observable

class RequestRepository(private val apiService: ApiService) {

    fun createTask(title: String, from: Int, to: Int): Observable<Any> {
        return apiService.createTask(TaskRequestModel(title, from, to))
    }

}
