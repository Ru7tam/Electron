package com.example.house_analysis.backend.api.authentication

data class auth_register_user(
    val birthday: String,
    val email: String,
    val fullname: String,
    val gender: String,
    val password: String,
    val phone: String
)