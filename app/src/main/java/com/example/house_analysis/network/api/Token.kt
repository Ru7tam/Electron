package com.example.house_analysis.network.api

object Token {
    private var token: String = ""

    fun setToken(token: String) {
        this.token = token
    }

    fun getToken(): String = token
}