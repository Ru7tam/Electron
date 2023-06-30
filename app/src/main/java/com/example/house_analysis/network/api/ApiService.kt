package com.example.house_analysis.network.api

import com.example.house_analysis.network.model.request.LoungeFloorModel
import com.example.house_analysis.network.model.request.TaskRequestModel
import com.example.house_analysis.network.model.request.UserLoginData
import com.example.house_analysis.network.model.request.UserRegisterData
import com.example.house_analysis.network.model.response.TasksResponse
import com.example.house_analysis.network.model.response.TokenResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("tasks")
    fun createTask(@Body request: TaskRequestModel): Observable<Any>

    @GET("tasks/user")
    fun getTasks(): Observable<ArrayList<TasksResponse>>

    @POST("auth")
    fun loginUser(@Body userData: UserLoginData): Observable<TokenResponse>

    @POST("auth/register")
    fun registerUser(@Body userData: UserRegisterData): Observable<Response<Unit>>

    @PATCH("tasks/{taskId}/subtasks/replace")
    fun replaceFloorAndLoungeForSubtask(@Path("taskId") taskId: Int, @Body request: LoungeFloorModel): Observable<Response<Unit>>


    companion object Factory {
        fun create(): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val newRequest = originalRequest.newBuilder()
                        .header("Accept", "*/*")
                        .header("Authorization", "Bearer ${Token.getToken()}")
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