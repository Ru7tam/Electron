package com.example.house_analysis.network.api.builtRequests

import android.util.Log
import com.example.house_analysis.network.model.response.TaskWithSubtasks
import com.example.house_analysis.network.model.response.TasksResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GetRequestsRepo(private val networkRepository: RequestRepository) {
    private val logTag = "Network"

    suspend fun getTasks(): ArrayList<TasksResponse> {
        return suspendCoroutine { continuation ->
            networkRepository.getTasks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        result.forEach {
                            Log.d(logTag, it.toString())
                        }
                        continuation.resume(result)
                    }, { error ->
                        Log.d(logTag, error.stackTraceToString())
                        continuation.resumeWithException(error)
                    }
                )
        }
    }

    fun requestGetTaskWithSubtasks(taskId: Int): TaskWithSubtasks {
        var response = TaskWithSubtasks(0, "null", 0, emptyList())
        networkRepository.getFullTaskWithSubtasks(taskId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    Log.d(logTag, result.toString())
                    response = result
                }, { error ->
                    Exception(error).printStackTrace()
                }
            )
        return response
    }
}
