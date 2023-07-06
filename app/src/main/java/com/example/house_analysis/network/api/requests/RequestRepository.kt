package com.example.house_analysis.network.api.requests

import com.example.house_analysis.network.api.RequestRepositoryProvider
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.request.UserLoginData
import com.example.house_analysis.network.model.request.UserRegisterData

class RequestRepository {
    private val networkRepository = RequestRepositoryProvider.provideRequestRepository()
    private val networkPostRepo = PostRequestsRepo(networkRepository)
    private val networkGetRepo = GetRequestsRepo(networkRepository)

    suspend fun login(userInfo: UserLoginData) = networkPostRepo.login(userInfo)
    suspend fun register(userInfo: UserRegisterData) = networkPostRepo.registration(userInfo)
    suspend fun getTasks() = networkGetRepo.getTasks()
    fun createTask(taskInfo: TaskRequestModel) { networkPostRepo.createTask(taskInfo) }

}