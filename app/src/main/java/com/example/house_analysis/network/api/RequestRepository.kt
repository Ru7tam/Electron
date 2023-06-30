package com.example.house_analysis.network.api

import com.example.house_analysis.network.model.request.LoungeFloorModel
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.request.UserLoginData
import com.example.house_analysis.network.model.request.UserRegisterData
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.network.model.response.TokenResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response

class RequestRepository(private val apiService: ApiService) {

    fun createTask(title: String, from: Int, to: Int): Observable<Any> {
        return apiService.createTask(TaskRequestModel(title, from, to))
    }

    fun getTasks(): Observable<ArrayList<TasksResponse>> {
        return apiService.getTasks()
    }

    fun loginUser(email: String, password: String): Observable<TokenResponse> {
        return apiService.loginUser(UserLoginData(email, password))
    }

    fun registerUser(fullName: String, email: String, password: String, gender: String, birthday: String, phone: String): Observable<Response<Unit>> {
        return apiService.registerUser(UserRegisterData(fullName, email, password, gender, birthday, phone))
    }

    fun replaceFloorAndLoungeForSubtask(taskIdToChange: Int, floors: Int, lounges: Int, subtaskIds: List<Int>): Observable<Response<Unit>> {
        return apiService.replaceFloorAndLoungeForSubtask(taskIdToChange, LoungeFloorModel(floors, lounges, subtaskIds))
    }

}
