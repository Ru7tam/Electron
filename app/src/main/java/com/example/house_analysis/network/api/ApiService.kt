package com.example.house_analysis.network.api

import com.example.house_analysis.network.model.request.TaskRequestModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

interface ApiService {

    @POST("tasks")
    fun createTask(@Body request: TaskRequestModel): Observable<Any>


    companion object Factory {
        fun create(): ApiService {
            val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW5pbXNlckBnbWFpbC5jb20iLCJpYXQiOjE2ODc2NDgxMDYsImV4cCI6MTY4NzY0OTU0Nn0.o3q-SbizINiOidsrx1NIiom4lN1EIX2-q-p0ck3POw4"
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val newRequest = originalRequest.newBuilder()
                        .header("Accept", "*/*")
                        .header("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(newRequest)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.electronos.ru/api/v1/")
                .build()

            return retrofit.create(ApiService::class.java);
        }
    }
}