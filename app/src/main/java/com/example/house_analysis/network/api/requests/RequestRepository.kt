package com.example.house_analysis.network.api.requests

import com.example.house_analysis.network.api.RequestRepositoryProvider
import com.example.house_analysis.network.api.requests.repos.DeleteRequestsRepo
import com.example.house_analysis.network.api.requests.repos.GetRequestsRepo
import com.example.house_analysis.network.api.requests.repos.PostRequestsRepo
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.request.UserLoginData
import com.example.house_analysis.network.model.request.UserRegisterData

class RequestRepository {
    private val networkRepository = RequestRepositoryProvider.provideRequestRepository()

    private val getRepo = GetRequestsRepo(networkRepository)
    private val postRepo = PostRequestsRepo(networkRepository)
    private val deleteRepo = DeleteRequestsRepo(networkRepository)

    suspend fun login(userInfo: UserLoginData) = postRepo.login(userInfo)
    suspend fun register(userInfo: UserRegisterData) = postRepo.registration(userInfo)
    suspend fun getTasks() = getRepo.getTasks()
    suspend fun getTask(taskId: Int) = getRepo.getTask(taskId)
    suspend fun createTask(taskInfo: TaskRequestModel) = postRepo.createTask(taskInfo)
    fun deleteTask(taskId: Int) = deleteRepo.deleteTask(taskId)
}