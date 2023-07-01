package com.example.house_analysis.network.api.builtRequests

import android.util.Log
import com.example.house_analysis.network.api.Token
import com.example.house_analysis.network.model.request.UserLoginData
import com.example.house_analysis.network.model.request.UserRegisterData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PostRequestsRepo(private val networkRepository: RequestRepository) {
    private val logTag = "Network"

    suspend fun login(userInfo: UserLoginData): Boolean {
        return suspendCoroutine { continuation ->
            networkRepository.loginUser(userInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        Token.setToken(result.token)
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

    fun createTask(address: String, from: Int, to: Int) {
        networkRepository.createTask(title = address, from = from, to = to)
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