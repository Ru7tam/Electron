package com.example.house_analysis.network.api

import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.request.UserData
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.network.model.response.TokenResponse
import io.reactivex.Observable

class RequestRepository(private val apiService: ApiService) {

    fun createTask(title: String, from: Int, to: Int): Observable<Any> {
        return apiService.createTask(TaskRequestModel(title, from, to))
    }

    fun getTasks(): Observable<ArrayList<TasksResponse>> {
        return apiService.getTasks()
    }

    fun loginUser(email: String, password: String): Observable<TokenResponse> {
        return apiService.loginUser(UserData(email, password))
    }

}
