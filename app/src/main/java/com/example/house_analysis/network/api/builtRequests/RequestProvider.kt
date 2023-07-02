package com.example.house_analysis.network.api.builtRequests

import com.example.house_analysis.network.api.ApiService
import com.example.house_analysis.network.model.request.LoungeFloorModel
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.request.UserLoginData
import com.example.house_analysis.network.model.request.UserRegisterData
import com.example.house_analysis.network.model.response.TaskWithSubtasks
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.network.model.response.TokenResponse
import io.reactivex.Observable
import retrofit2.Response

class RequestProvider(private val apiService: ApiService) {

    fun loginUser(userInfo: UserLoginData): Observable<TokenResponse> {
        return apiService.loginUser(userInfo)
    }

    fun registerUser(userInfo: UserRegisterData): Observable<Response<Unit>> {
        return apiService.registerUser(userInfo)
    }

    fun createTask(title: String, from: Int, to: Int): Observable<Any> {
        return apiService.createTask(TaskRequestModel(title, from, to))
    }

    fun getTasks(): Observable<ArrayList<TasksResponse>> {
        return apiService.getUserTasks()
    }

    fun getFullTaskWithSubtasks(taskId: Int): Observable<TaskWithSubtasks> {
        return apiService.getFullTaskWithSubtasks(taskId)
    }

    fun replaceFloorAndLoungeForSubtask(taskIdToChange: Int, floors: Int, lounges: Int, subtaskIds: List<Int>): Observable<Response<Unit>> {
        return apiService.replaceFloorAndLoungeForSubtask(taskIdToChange, LoungeFloorModel(floors, lounges, subtaskIds))
    }

}
