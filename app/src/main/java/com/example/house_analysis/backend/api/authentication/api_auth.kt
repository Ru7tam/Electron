package com.example.house_analysis.backend.api.authentication

import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface api_auth {
    @POST("auth/register")
    fun register_user(@Body requestBody: RequestBody): Call<auth_register_user>
}