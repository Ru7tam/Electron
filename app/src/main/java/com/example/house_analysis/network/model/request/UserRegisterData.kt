package com.example.house_analysis.network.model.request

data class UserRegisterData(
    val fullname: String,
    val email: String,
    val password: String,
    val gender: String,
    val birthday: String,
    val phone: String
)