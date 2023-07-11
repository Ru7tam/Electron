package com.example.house_analysis.network.api.requests

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

    fun createTask(taskInfo: TaskRequestModel): Observable<TasksResponse> {
        return apiService.createTask(taskInfo)
    }

    fun deleteTask(taskId: Int) {
        return apiService.deleteTask(taskId)
    }

    fun getTasks(): Observable<ArrayList<TasksResponse>> {
        return apiService.getUserTasks()
    }

    fun getTask(taskId: Int): Observable<TasksResponse> {
        return apiService.getTask(taskId)
    }

    fun getFullTaskWithSubtasks(taskId: Int): Observable<TaskWithSubtasks> {
        return apiService.getFullTaskWithSubtasks(taskId)
    }

    fun replaceFloorAndLoungeForSubtask(taskIdToChange: Int, floors: Int, lounges: Int, subtaskIds: List<Int>): Observable<Response<Unit>> {
        return apiService.replaceFloorAndLoungeForSubtask(taskIdToChange, LoungeFloorModel(floors, lounges, subtaskIds))
    }

}