package com.example.house_analysis.network.api.requests

import android.util.Log
import com.example.house_analysis.network.api.ApiService
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.request.UserLoginData
import com.example.house_analysis.network.model.request.UserRegisterData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PostRequestsRepo(private val networkRepository: RequestProvider) {
    private val logTag = "Network"

    suspend fun login(userInfo: UserLoginData): Boolean {
        return suspendCoroutine { continuation ->
            networkRepository.loginUser(userInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        ApiService.token = result.token
                        Log.d(logTag, result.toString())
                        continuation.resume(true)
                    }, { error ->
                        Log.d(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

    suspend fun registration(userInfo: UserRegisterData): Int {
        return suspendCoroutine { continuation ->
            networkRepository.registerUser(userInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        Log.d("Network", result.toString())
                        continuation.resume(result.code())
                    }, { error ->
                        Log.d(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

    fun createTask(taskInfo: TaskRequestModel) {
        networkRepository.createTask(taskInfo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Log.d(logTag, result.toString())
                }, { error ->
                    Exception(error).printStackTrace()
                }
            )
    }
}